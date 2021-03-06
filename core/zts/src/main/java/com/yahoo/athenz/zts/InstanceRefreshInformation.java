//
// This file generated by rdl 1.4.14. Do not modify!
//

package com.yahoo.athenz.zts;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yahoo.rdl.*;

//
// InstanceRefreshInformation -
//
public class InstanceRefreshInformation {
    @RdlOptional
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String csr;
    @RdlOptional
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String ssh;
    @RdlOptional
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Boolean token;

    public InstanceRefreshInformation setCsr(String csr) {
        this.csr = csr;
        return this;
    }
    public String getCsr() {
        return csr;
    }
    public InstanceRefreshInformation setSsh(String ssh) {
        this.ssh = ssh;
        return this;
    }
    public String getSsh() {
        return ssh;
    }
    public InstanceRefreshInformation setToken(Boolean token) {
        this.token = token;
        return this;
    }
    public Boolean getToken() {
        return token;
    }

    @Override
    public boolean equals(Object another) {
        if (this != another) {
            if (another == null || another.getClass() != InstanceRefreshInformation.class) {
                return false;
            }
            InstanceRefreshInformation a = (InstanceRefreshInformation) another;
            if (csr == null ? a.csr != null : !csr.equals(a.csr)) {
                return false;
            }
            if (ssh == null ? a.ssh != null : !ssh.equals(a.ssh)) {
                return false;
            }
            if (token == null ? a.token != null : !token.equals(a.token)) {
                return false;
            }
        }
        return true;
    }
}
