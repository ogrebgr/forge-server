package com.bolyartech.forge.server.misc;

import com.bolyartech.forge.server.route.InvalidParameterValue;
import com.bolyartech.forge.server.route.MissingParameterValue;
import com.bolyartech.forge.server.route.RequestContext;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static com.bolyartech.forge.server.misc.ForgeMessageFormat.format;


/**
 * Utility class for GET/POST/PI parameters
 */
public class Params {
    private static final String GET = "GET";
    private static final String POST = "POST";


    /**
     * Non-instantiable utility class
     */
    private Params() {
        throw new AssertionError();
    }

    public static void allPresentOrDie(String... pars) throws MissingParameterValue {
        if (pars == null || pars.length == 0) {
            throw new IllegalArgumentException("pars is empty");
        }

        for (String par : pars) {
            if (Strings.isNullOrEmpty(par)) {
                throw new MissingParameterValue(par);
            }
        }
    }


    /**
     * Checks if all strings are non-null and non-empty
     *
     * @param pars Strings to be checked
     * @return true if all strings are non-null and non-empty
     */
    public static boolean areAllPresent(String... pars) {
        boolean ret = true;

        if (pars == null || pars.length == 0) {
            throw new IllegalArgumentException("pars is empty");
        }

        for (String par : pars) {
            if (Strings.isNullOrEmpty(par)) {
                ret = false;
                break;
            }
        }

        return ret;
    }


    /**
     * Extracts long parameter's value from POST parameters
     *
     * @param ctx           Context from which the value will be extracted
     * @param parameterName Parameter name
     * @return extracted value
     * @throws MissingParameterValue if there is no value for this parameter
     * @throws InvalidParameterValue if the value is present but cannot be parsed as long
     */
    public static long extractLongFromPost(@Nonnull RequestContext ctx, @Nonnull String parameterName)
            throws MissingParameterValue, InvalidParameterValue {

        String s = ctx.getFromPost(parameterName);

        return extractLongHelper(parameterName, s, POST);
    }


    /**
     * Extracts long parameter's value from GET parameters
     *
     * @param ctx           Context from which the value will be extracted
     * @param parameterName Parameter name
     * @return extracted value
     * @throws MissingParameterValue if there is no value for this parameter
     * @throws InvalidParameterValue if the value is present but cannot be parsed as long
     */
    public static long extractLongFromGet(@Nonnull RequestContext ctx, @Nonnull String parameterName)
            throws MissingParameterValue, InvalidParameterValue {

        String s = ctx.getFromGet(parameterName);

        return extractLongHelper(parameterName, s, GET);
    }


    private static long extractLongHelper(@Nonnull String parameterName,
                                          @Nullable String value,
                                          @Nonnull String type)
            throws MissingParameterValue, InvalidParameterValue {

        if (value == null) {
            throw new MissingParameterValue(parameterName);
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new InvalidParameterValue(parameterName);
        }
    }


    /**
     * Extracts integer parameter's value from POST parameters
     *
     * @param ctx           Context from which the value will be extracted
     * @param parameterName Parameter name
     * @return extracted value
     * @throws MissingParameterValue if there is no value for this parameter
     * @throws InvalidParameterValue if the value is present but cannot be parsed as int
     */
    public static int extractIntFromPost(@Nonnull RequestContext ctx, @Nonnull String parameterName)
            throws MissingParameterValue, InvalidParameterValue {

        String s = ctx.getFromPost(parameterName);

        return extractIntHelper(parameterName, s, POST);
    }


    /**
     * Extracts integer parameter's value from GET parameters
     *
     * @param ctx           Context from which the value will be extracted
     * @param parameterName Parameter name
     * @return extracted value
     * @throws MissingParameterValue if there is no value for this parameter
     * @throws InvalidParameterValue if the value is present but cannot be parsed as int
     */
    public static int extractIntFromGet(@Nonnull RequestContext ctx, @Nonnull String parameterName)
            throws MissingParameterValue, InvalidParameterValue {

        String s = ctx.getFromGet(parameterName);

        return extractIntHelper(parameterName, s, GET);
    }


    private static int extractIntHelper(@Nonnull String parameterName,
                                        @Nullable String value,
                                        @Nonnull String type)
            throws MissingParameterValue, InvalidParameterValue {

        if (value == null) {
            throw new MissingParameterValue(parameterName);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidParameterValue(parameterName);
        }
    }


    public static Optional<Integer> optIntFromPost(@Nonnull RequestContext ctx, @Nonnull String parameterName)
            throws InvalidParameterValue {

        String s = ctx.getFromPost(parameterName);

        return optIntHelper(parameterName, s, POST);
    }


    private static Optional<Integer> optIntHelper(@Nonnull String parameterName, @Nullable String value, @Nonnull String type)
            throws InvalidParameterValue {

        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            throw new InvalidParameterValue(parameterName);
        }
    }
}
