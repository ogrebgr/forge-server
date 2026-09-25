package com.bolyartech.forge.server.db

import org.slf4j.Logger
import java.sql.Connection
import java.sql.SQLException


/**
 * @throws DbTransactionRetryFailedException when after retrying maxRetries without success have given up
 */
@Deprecated("Db retries by itself")
@Throws(DbTransactionRetryFailedException::class)
fun <T> transIsolationRetry(
    dbc: Connection,
    isolationLevel: TransactionIsolationLevel,
    maxRetries: Int = 5,
    initialBackoff: Long = 100L,
    f: () -> T
): T {
    var retry = 0

    val initialIsolationLevel = dbc.transactionIsolation
    do {
        if (Thread.currentThread().isInterrupted) {
            break
        }

        try {
            dbc.autoCommit = false
            dbc.transactionIsolation = isolationLevel.code

            val ret = f()
            dbc.commit()

            return ret
        } catch (e: SQLException) {
            dbc.rollback()
            if (e.sqlState == "40001" || e.sqlState == "40P01") {
                if (initialBackoff > 0) {
                    Thread.sleep(initialBackoff * (retry + 1))
                }
                retry++
            } else {
                throw e
            }
        } catch (e: Exception) {
            dbc.rollback()
            throw e
        } finally {
            dbc.transactionIsolation = initialIsolationLevel
            dbc.autoCommit = true
        }
    } while (retry < maxRetries && !Thread.currentThread().isInterrupted)

    if (retry == maxRetries) {
        throw DbTransactionRetryFailedException("Max retries exceeded")
    }

    if (Thread.currentThread().isInterrupted) {
        throw DbTransactionRetryFailedException("Thread interrupted")
    }

    throw IllegalStateException("Should not happen")
}

fun simpleTrans(
    dbc: Connection,
    f: () -> Unit
) {
    try {
        dbc.autoCommit = false

        f()

        dbc.commit()
    } catch (e: Exception) {
        dbc.rollback()
        throw e
    } finally {
        dbc.autoCommit = true
    }
}

fun executeInTrans(
    dbc: Connection,
    logger: Logger,
    f: () -> Unit
) {
    try {
        dbc.autoCommit = false

        f()

        dbc.commit()
    } catch (e: Exception) {
        logger.error("Error in DB trans", e)
        dbc.rollback()
        throw e
    } finally {
        dbc.autoCommit = true
    }
}

fun <T> executeInTrans(
    dbc: Connection,
    logger: Logger,
    f: () -> T
): T {
    try {
        dbc.autoCommit = false

        val ret: T = f()

        dbc.commit()
        return ret
    } catch (e: Exception) {
        logger.error("Error in DB trans", e)
        dbc.rollback()
        throw e
    } finally {
        dbc.autoCommit = true
    }
}

/**
 * Executes a transaction and commits it if the function returns normally.
 * If the function throws an exception, the transaction is rolled back.
 * Please note that using `return@executeInTrans` in the middle of the block will commit the transaction.
 */
fun executeInTrans(
    dbc: Connection,
    f: () -> Unit
) {
    try {
        dbc.autoCommit = false

        f()

        dbc.commit()
    } catch (e: Exception) {
        dbc.rollback()
        throw e
    } finally {
        dbc.autoCommit = true
    }
}

/**
 * Executes a transaction and commits it if the function returns normally.
 * If the function throws an exception, the transaction is rolled back.
 * Please note that using `return@executeInTrans` in the middle of the block will commit the transaction.
 */
fun <T> executeInTrans(
    dbc: Connection,
    f: () -> T
): T {
    try {
        dbc.autoCommit = false

        val ret: T = f()

        dbc.commit()
        return ret
    } catch (e: Exception) {
        dbc.rollback()
        throw e
    } finally {
        dbc.autoCommit = true
    }
}

class DbTransactionRetryFailedException(message: String?) : Exception(message)


enum class TransactionIsolationLevel(val code: Int) {
    TRANSACTION_NONE(0),
    TRANSACTION_READ_UNCOMMITTED(1),
    TRANSACTION_READ_COMMITTED(2),
    TRANSACTION_REPEATABLE_READ(3),
    TRANSACTION_SERIALIZABLE(8);
}


sealed interface TransResult<out T> {
    data class Commit<T>(val value: T) : TransResult<T>
    data class Rollback<T>(val value: T) : TransResult<T>
}

/**
 * Executes a transaction and commits it if the block returns `TransResult.Commit`.
 * If `TransResult.Rollback` is returned, the transaction is rolled back.
 * More robust transaction execution that forces the user to explicitly return success or failure.
 * This prevents the user from using return@executeInTrans and accidentally commiting the transaction when he actually wants to rollback.
 */
fun <T> executeInTransR(dbc: Connection, f: () -> TransResult<T>): T {
    try {
        dbc.autoCommit = false
        return when (val r = f()) {
            is TransResult.Commit -> {
                dbc.commit(); r.value
            }

            is TransResult.Rollback -> {
                dbc.rollback(); r.value
            }
        }
    } catch (e: Exception) {
        dbc.rollback(); throw e
    } finally {
        dbc.autoCommit = true
    }
}