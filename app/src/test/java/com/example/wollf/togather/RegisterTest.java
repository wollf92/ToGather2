package com.example.wollf.togather;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by wollf on 15-1-2018.
 */

public class RegisterTest {
    @Test
    public void test_EmailValidator(){
        Register r = new Register();
        try{
            Method m = r.getClass().getDeclaredMethod("isEmailValid", String.class);
            m.setAccessible(true);
            //CORRECT EMAILS
            Assert.assertEquals("Valid email",true, (Boolean)m.invoke(r, "email@domain.com"));
            Assert.assertEquals("Email contains dot in the address field",true, (Boolean)m.invoke(r, "firstname.lastname@domain.com"));
            Assert.assertEquals("Email contains dot with subdomain",true, (Boolean)m.invoke(r, "email@subdomain.domain.com"));
            Assert.assertEquals("Plus sign is considered valid character",true, (Boolean)m.invoke(r, "firstname+lastname@domain.com"));
            Assert.assertEquals("Domain is valid IP address",true, (Boolean)m.invoke(r, "email@123.123.123.123"));
            Assert.assertEquals("Square bracket around IP address is considered valid",true, (Boolean)m.invoke(r, "email@[123.123.123.123]"));
            Assert.assertEquals("Quotes around email is considered valid",true, (Boolean)m.invoke(r, "\"email\"@domain.com"));
            Assert.assertEquals("Digits in address are valid",true, (Boolean)m.invoke(r, "1234567890@domain.com"));
            Assert.assertEquals("Dash in domain name is valid",true, (Boolean)m.invoke(r, "email@domain-one.com"));
            Assert.assertEquals("Underscore in the address field is valid",true, (Boolean)m.invoke(r, "_______@domain.com"));
            Assert.assertEquals(".name is valid Top Level Domain name",true, (Boolean)m.invoke(r, "email@domain.name"));
            Assert.assertEquals("Dot in Top Level Domain name also considered valid",true, (Boolean)m.invoke(r, "email@domain.co.jp"));
            Assert.assertEquals("Dash in address field is valid",true, (Boolean)m.invoke(r, "firstname-lastname@domain.com"));

            Assert.assertEquals("Missing @ sign and domain", false, (Boolean)m.invoke(r, "plainaddress"));
            Assert.assertEquals("Garbage", false, (Boolean)m.invoke(r, "#@%^%#$@#$@#.com"));
            Assert.assertEquals("Missing username", false, (Boolean)m.invoke(r, "@domain.com"));
            Assert.assertEquals("Encoded html within email is invalid", false, (Boolean)m.invoke(r, "Joe Smith <email@domain.com>"));
            Assert.assertEquals("Missing @", false, (Boolean)m.invoke(r, "email.domain.com"));
            Assert.assertEquals("Two @ sign", false, (Boolean)m.invoke(r, "email@domain@domain.com"));
            Assert.assertEquals("Leading dot in address is not allowed", false, (Boolean)m.invoke(r, ".email@domain.com"));
            Assert.assertEquals("Trailing dot in address is not allowed", false, (Boolean)m.invoke(r, "email.@domain.com"));
            Assert.assertEquals("Multiple dots", false, (Boolean)m.invoke(r, "email..email@domain.com"));
            Assert.assertEquals("Unicode char as address", false, (Boolean)m.invoke(r, "あいうえお@domain.com"));
            Assert.assertEquals("Text followed email is not allowed", false, (Boolean)m.invoke(r, "email@domain.com (Joe Smith)"));
            Assert.assertEquals("Missing top level domain (.com/.net/.org/etc)", false, (Boolean)m.invoke(r, "email@domain"));
            Assert.assertEquals("Leading dash in front of domain is invalid", false, (Boolean)m.invoke(r, "email@-domain.com"));
            Assert.assertEquals("Multiple dot in the domain portion is invalid", false, (Boolean)m.invoke(r, "email@domain..com"));
        } catch (Exception e){
            assert false;
        }
    }
}
