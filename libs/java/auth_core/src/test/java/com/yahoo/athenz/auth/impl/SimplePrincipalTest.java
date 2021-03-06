/**
 * Copyright 2016 Yahoo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yahoo.athenz.auth.impl;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.yahoo.athenz.auth.Authority;
import com.yahoo.athenz.auth.Principal;
import com.yahoo.athenz.auth.impl.SimplePrincipal;
import com.yahoo.athenz.auth.impl.UserAuthority;

public class SimplePrincipalTest {

    String fakeUnsignedCreds = "v=U1;d=user;n=jdoe";
    String fakeCreds = fakeUnsignedCreds + ";s=signature";
    
    @Test
    public void testSimplePrincipal() {
        String testApplicationId = "test_app_id";
        SimplePrincipal p = (SimplePrincipal) SimplePrincipal.create("user", "jdoe", fakeCreds, null);
        assertNotNull(p);
        p.setUnsignedCreds(fakeUnsignedCreds);
        p.setApplicationId(testApplicationId);
        assertEquals(p.getName(), "jdoe");
        assertEquals(p.getDomain(), "user");
        assertEquals(p.getCredentials(), fakeCreds);
        assertEquals(p.getUnsignedCredentials(), fakeUnsignedCreds);
        assertEquals(p.getApplicationId(), testApplicationId);
    }
    
    @Test
    public void testSimplePrincipalNullUnsignedCred() {
        Principal p = SimplePrincipal.create("user", "jdoe", fakeCreds, null);
        assertNotNull(p);
        assertEquals(p.getName(), "jdoe");
        assertEquals(p.getDomain(), "user");
        assertEquals(p.getCredentials(), fakeCreds);
        assertNull(p.getUnsignedCredentials());
    }
    
    @Test
    public void testFullName() {
        Principal p = SimplePrincipal.create("user", "jdoe", fakeCreds, null);
        assertEquals(p.getFullName(), "user.jdoe");
        
        p = SimplePrincipal.create(null, "jdoe", fakeCreds);
        assertEquals(p.getFullName(), "jdoe");
        
        List<String> roles = new ArrayList<String>();
        roles.add("role1");
        
        p = SimplePrincipal.create("user", fakeCreds, roles, null);
        assertEquals(p.getFullName(), "user");
        
        Authority a = null;
        p = SimplePrincipal.create(null, null, a);
        assertNull(p.getFullName());
    }
    
    @Test
    public void testSimplePrincipalInvalidDomain() {
        
        // we output warning but still create a principal
        
        List<String> roles = new ArrayList<String>();
        roles.add("storage.tenant.weather.updater");
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        SimplePrincipal p = (SimplePrincipal) SimplePrincipal.create("user test invalid#$%",
                fakeCreds, roles, userAuthority);
        assertNotNull(p);
        p.setUnsignedCreds(fakeUnsignedCreds);
        assertEquals(p.getDomain(), "user test invalid#$%");
        assertEquals(p.getCredentials(), fakeCreds);
        assertEquals(p.getUnsignedCredentials(), fakeUnsignedCreds);
    }
    
    @Test
    public void testSimplePrincipalNullRole() {
        
        // we output warning but still create a principal

        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        SimplePrincipal p = (SimplePrincipal) SimplePrincipal.create("user",
                fakeCreds, (List<String>) null, userAuthority);
        assertNotNull(p);
        p.setUnsignedCreds(fakeUnsignedCreds);
        assertEquals(p.getDomain(), "user");
        assertEquals(p.getCredentials(), fakeCreds);
        assertEquals(p.getUnsignedCredentials(), fakeUnsignedCreds);
    }
    
    @Test
    public void testSimplePrincipalEmptyRole() {
        
        // we output warning but still create a principal

        List<String> roles = new ArrayList<String>();
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        SimplePrincipal p = (SimplePrincipal) SimplePrincipal.create("user", fakeCreds, roles, userAuthority);
        assertNotNull(p);
        p.setUnsignedCreds(fakeUnsignedCreds);
        assertEquals(p.getDomain(), "user");
        assertEquals(p.getCredentials(), fakeCreds);
        assertEquals(p.getUnsignedCredentials(), fakeUnsignedCreds);
    }
    
    @Test
    public void testSimplePrincipalInvalidName() {
        
        // we output warning but still create a principal
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        SimplePrincipal p = (SimplePrincipal) SimplePrincipal.create("user", "jdoe invalid#$%",
                fakeCreds, 0, userAuthority);
        assertNotNull(p);
        p.setUnsignedCreds(fakeUnsignedCreds);
        assertEquals(p.getDomain(), "user");
        assertEquals(p.getName(), "jdoe invalid#$%");
        assertEquals(p.getCredentials(), fakeCreds);
        assertEquals(p.getUnsignedCredentials(), fakeUnsignedCreds);
    }
    
    @Test
    public void testSimplePrincipalNullDomainAuthorityDomainNotNull() {
        
        // we output warning but still create a principal
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create(null, "jdoe", fakeCreds, 0, userAuthority);
        assertNull(p);
    }
    
    @Test
    public void testSimplePrincipalNullDomainAuthorityDomainNull() {
        
        // we output warning but still create a principal

        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create(null, "jdoe", fakeCreds, 0, null);
        assertNull(p.getDomain());
    }
    
    @Test
    public void testSimplePrincipalDomainNoMatch() {
        
        // we output warning but still create a principal
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create("coretech", "jdoe", fakeCreds, 0, userAuthority);
        assertNull(p);
    }
    
    @Test
    public void testSimplePrincipalIssueTime() {
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create("user", "jdoe", fakeCreds, 101, userAuthority);
        assertNotNull(p);
        assertEquals(p.getIssueTime(), 101);
    }
    
    @Test
    public void testSimplePrincipalToStringZToken() {
        
        List<String> roles = new ArrayList<String>();
        roles.add("updater");
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create("user", fakeCreds, roles, userAuthority);
        assertEquals(p.toString(), "ZToken_user~updater");
    }
    
    @Test
    public void testSimplePrincipalToStringUser() {
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create("user", "jdoe", fakeCreds, 101, userAuthority);
        assertEquals(p.toString(), "user.jdoe");
    }
    
    @Test
    public void testSimplePrincipalExtraFields() {
        
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.initialize();

        Principal p = SimplePrincipal.create("user", "jdoe", fakeCreds, 101, userAuthority);
        ((SimplePrincipal) p).setOriginalRequestor("athenz.ci");
        ((SimplePrincipal) p).setKeyService("zts");
        ((SimplePrincipal) p).setKeyId("v1");

        assertEquals(p.toString(), "user.jdoe");
        assertEquals(p.getOriginalRequestor(), "athenz.ci");
        assertEquals(p.getKeyService(), "zts");
        assertEquals(p.getKeyId(), "v1");
    }

    @Test
    public void testSimplePrincipalIP() {
        SimplePrincipal check = (SimplePrincipal) SimplePrincipal.create("user", "jdoe", "hoge");
        
        check.setIP("10.10.10.10");
        assertEquals(check.getIP(),"10.10.10.10");
    }

    @Test
    public void testSimplePrincipalAuthorityCreate() {
        Authority hoge = Mockito.mock(Authority.class);
        
        SimplePrincipal check = (SimplePrincipal) SimplePrincipal.create("user", "jdoe", hoge);
        assertNotNull(check);

        Mockito.when(hoge.getDomain()).thenReturn(null);
        check = (SimplePrincipal) SimplePrincipal.create(null, "jdoe","hoge", 0, hoge);
        assertNotNull(check);
    }
}
