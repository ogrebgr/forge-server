package com.bolyartech.forge.server.misc;

import com.bolyartech.forge.server.route.InvalidParameterValue;
import com.bolyartech.forge.server.route.MissingParameterValue;
import com.bolyartech.forge.server.route.RequestContext;
import com.google.common.base.Strings;

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
    public static long extractLongFromPost(@NonNull RequestContext ctx, @NonNull String parameterName)
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
    public static long extractLongFromGet(@NonNull RequestContext ctx, @NonNull String parameterName)
            throws MissingParameterValue, InvalidParameterValue {

        String s = ctx.getFromGet(parameterName);

        return extractLongHelper(parameterName, s, GET);
    }


    private static long extractLongHelper(@NonNull String parameterName,
                                          @Nullable String value,
                                          @NonNull String type)
            throws MissingParameterValue, InvalidParameterValue {

        if (value == null) {
            throw new MissingParameterValue("Missing " + type + " value for parameter " + parameterName);
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new InvalidParameterValue(format("Invalid value {} for parameter {}",
                    value, parameterName));
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
    public static int extractIntFromPost(@NonNull RequestContext ctx, @NonNull String parameterName)
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
    public static int extractIntFromGet(@NonNull RequestContext ctx, @NonNull String parameterName)
            throws MissingParameterValue, InvalidParameterValue {

        String s = ctx.getFromGet(parameterName);

        return extractIntHelper(parameterName, s, GET);
    }


    private static int extractIntHelper(@NonNull String parameterName,
                                        @Nullable String value,
                                        @NonNull String type)
            throws MissingParameterValue, InvalidParameterValue {

        if (value == null) {
            throw new MissingParameterValue("Missing " + type + " value for parameter " + parameterName);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidParameterValue(format("Invalid value {} for parameter {}",
                    value, parameterName));
        }
    }

}
