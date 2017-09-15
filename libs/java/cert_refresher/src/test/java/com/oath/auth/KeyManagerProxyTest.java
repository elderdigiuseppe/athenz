package com.oath.auth;

/**
 * Copyright 2017 Yahoo Holdings, Inc.
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

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.X509KeyManager;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.junit.Assert.*;

public class KeyManagerProxyTest {

    private X509KeyManager generateNewKeyManger() {
        return new X509KeyManager() {
            @Override
            public String[] getClientAliases(String s, Principal[] principals) {
                return new String[0];
            }

            @Override
            public String chooseClientAlias(String[] strings, Principal[] principals, Socket socket) {
                return null;
            }

            @Override
            public String[] getServerAliases(String s, Principal[] principals) {
                return new String[0];
            }

            @Override
            public String chooseServerAlias(String s, Principal[] principals, Socket socket) {
                return null;
            }

            @Override
            public X509Certificate[] getCertificateChain(String s) {
                return new X509Certificate[0];
            }

            @Override
            public PrivateKey getPrivateKey(String s) {
                return null;
            }
        };
    }

    @Test
    public void testKeyManagerProxySetKeyManger() {
        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger() };

        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);
        X509KeyManager keyManagerFirst = Deencapsulation.getField(keyManagerProxy, "keyManager");

        assertNotNull(keyManagerFirst);


        keyManagerProxy.setKeyManager(new KeyManager[] { generateNewKeyManger() });

        X509KeyManager keyManagerSecond = Deencapsulation.getField(keyManagerProxy, "keyManager");
        assertNotNull(keyManagerSecond);

        assertNotSame(keyManagerFirst, keyManagerSecond);
    }

    @Test
    public void testKeyManagerProxyGeClientAliases(@Mocked X509KeyManager mockedKeyManager) throws CertificateException {
        new Expectations() {{
            mockedKeyManager.getClientAliases("cert", (Principal[]) any); times = 1;
        }};

        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger()};
        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);

        keyManagerProxy.getClientAliases("cert", null);
    }

    @Test
    public void testKeyManagerProxyChooseClientAlias(@Mocked X509KeyManager mockedKeyManager) throws CertificateException {
        new Expectations() {{
            mockedKeyManager.chooseClientAlias((String[]) any, (Principal[]) any, (Socket) any); times = 1;
        }};

        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger() };
        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);

        keyManagerProxy.chooseClientAlias(null, null, null);
    }

    @Test
    public void testKeyManagerProxyGetServerAliases(@Mocked X509KeyManager mockedKeyManager) throws CertificateException {
        new Expectations() {{
            mockedKeyManager.getServerAliases("cert", (Principal[]) any); times = 1;
        }};

        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger()};
        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);

        keyManagerProxy.getServerAliases("cert", null);
    }

    @Test
    public void testKeyManagerProxyChooseServerAlias(@Mocked X509KeyManager mockedKeyManager) throws CertificateException {
        new Expectations() {{
            mockedKeyManager.chooseServerAlias("cert", (Principal[]) any, (Socket) any); times = 1;
        }};

        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger()};
        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);

        keyManagerProxy.chooseServerAlias("cert", null, null);
    }

    @Test
    public void testKeyManagerProxyGetCertificateChain(@Mocked X509KeyManager mockedKeyManager) throws CertificateException {
        new Expectations() {{
            mockedKeyManager.getCertificateChain("cert"); times = 1;
        }};

        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger()};
        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);

        keyManagerProxy.getCertificateChain("cert");
    }

    @Test
    public void testKeyManagerProxyGetPrivateKey(@Mocked X509KeyManager mockedKeyManager) throws CertificateException {
        new Expectations() {{
            mockedKeyManager.getPrivateKey("cert"); times = 1;
        }};

        KeyManager[] keyManagers = new KeyManager[] { generateNewKeyManger()};
        KeyManagerProxy keyManagerProxy = new KeyManagerProxy(keyManagers);

        keyManagerProxy.getPrivateKey("cert");
    }

}
