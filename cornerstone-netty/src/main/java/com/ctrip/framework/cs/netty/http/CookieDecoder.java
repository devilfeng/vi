package com.ctrip.framework.cs.netty.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;

import static com.ctrip.framework.cs.netty.http.CookieUtil.*;

/**
 * Created by jiang.j on 2017/2/14.
 */
public abstract class CookieDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final boolean strict;

    protected CookieDecoder(boolean strict) {
        this.strict = strict;
    }

    protected DefaultCookie initCookie(String header, int nameBegin, int nameEnd, int valueBegin, int valueEnd) {
        if (nameBegin == -1 || nameBegin == nameEnd) {
            logger.debug("Skipping cookie with null name");
            return null;
        }

        if (valueBegin == -1) {
            logger.debug("Skipping cookie with null value");
            return null;
        }

        CharSequence wrappedValue = CharBuffer.wrap(header, valueBegin, valueEnd);
        CharSequence unwrappedValue = unwrapValue(wrappedValue);
        if (unwrappedValue == null) {
            logger.debug("Skipping cookie because starting quotes are not properly balanced in '{}'",
                    wrappedValue);
            return null;
        }

        final String name = header.substring(nameBegin, nameEnd);

        int invalidOctetPos;
        if (strict && (invalidOctetPos = firstInvalidCookieNameOctet(name)) >= 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping cookie because name '{}' contains invalid char '{}'",
                        name, name.charAt(invalidOctetPos));
            }
            return null;
        }

        final boolean wrap = unwrappedValue.length() != valueEnd - valueBegin;

        if (strict && (invalidOctetPos = firstInvalidCookieValueOctet(unwrappedValue)) >= 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping cookie because value '{}' contains invalid char '{}'",
                        unwrappedValue, unwrappedValue.charAt(invalidOctetPos));
            }
            return null;
        }

        DefaultCookie cookie = new DefaultCookie(name, unwrappedValue.toString());
        cookie.setWrap(wrap);
        return cookie;
    }
}
