package com.bolyartech.forge.server.route

import com.bolyartech.forge.server.HttpMethod
import com.bolyartech.forge.server.handler.RouteHandler
import com.bolyartech.forge.server.response.Response
import org.junit.jupiter.api.Test

class RouteRegisterImplTest {
    companion object {
        private const val MODULE_NAME = "some_mod"
    }

    @Test
    fun testRegistrationSorting() {
        val reg = RouteRegisterImpl()

        val handler = object : RouteHandler {
            override fun handle(ctx: RequestContext): Response {
                TODO("Not yet implemented")
            }
        }
        reg.register(MODULE_NAME, RouteStartsWith(HttpMethod.GET, "/", handler))
        reg.register(MODULE_NAME, RouteStartsWith(HttpMethod.GET, "/presni/", handler))
        reg.register(MODULE_NAME, RouteStartsWith(HttpMethod.GET, "/presni/chudesni/", handler))
        reg.register(MODULE_NAME, RouteStartsWith(HttpMethod.GET, "/test/", handler))

        val eps = reg.getEndpointsGetStartsWith()
        assert(eps[0].route.getPath() == "/presni/chudesni/")
        assert(eps[3].route.getPath() == "/")
    }

    @Test
    fun testMatchExact() {
        val reg = RouteRegisterImpl()

        val handler = object : RouteHandler {
            override fun handle(ctx: RequestContext): Response {
                TODO("Not yet implemented")
            }
        }
        reg.register(MODULE_NAME, RouteStartsWith(HttpMethod.GET, "/", handler))
        reg.register(MODULE_NAME, RouteExact(HttpMethod.GET, "/", handler))

        val m = reg.match(HttpMethod.GET, "/")
    }

}
