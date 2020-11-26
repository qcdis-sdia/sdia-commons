package nl.uva.qcdis.sdia.model.tosca;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

/**
 * CredentialOS
 *
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
        date = "2020-11-26T12:21:01.264Z[GMT]")

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialOS extends Credential {

    @JsonProperty("os_auth_url")
    private String osAuthUrl = null;

    @JsonProperty("os_project_id")
    private String osProjectId = null;

    @JsonProperty("os_auth_type")
    private String osAuthType = null;

    @JsonProperty("os_identity_provider")
    private String osIdentityProvider = null;

    @JsonProperty("os_protocol")
    private String osProtocol = null;

    public CredentialOS osAuthUrl(String osAuthUrl) {
        this.osAuthUrl = osAuthUrl;
        return this;
    }

    /**
     * the Keystone url. example https://stack-server.ct.infn.it:35357/v3
     *
     * @return osAuthUrl
     *
     */
    public String getOsAuthUrl() {
        return osAuthUrl;
    }

    public void setOsAuthUrl(String osAuthUrl) {
        this.osAuthUrl = osAuthUrl;
    }

    public CredentialOS osProjectId(String osProjectId) {
        this.osProjectId = osProjectId;
        return this;
    }

    /**
     * the project example 745695ccd17042fabf96d2410a4278d9
     *
     * @return osProjectId
     *
     */
    public String getOsProjectId() {
        return osProjectId;
    }

    public void setOsProjectId(String osProjectId) {
        this.osProjectId = osProjectId;
    }

    public CredentialOS osAuthType(String osAuthType) {
        this.osAuthType = osAuthType;
        return this;
    }

    /**
     * example v3oidcaccesstoken
     *
     * @return osAuthType
     *
     */
    public String getOsAuthType() {
        return osAuthType;
    }

    public void setOsAuthType(String osAuthType) {
        this.osAuthType = osAuthType;
    }

    public CredentialOS osIdentityProvider(String osIdentityProvider) {
        this.osIdentityProvider = osIdentityProvider;
        return this;
    }

    /**
     * example egi.eu
     *
     * @return osIdentityProvider
     *
     */
    public String getOsIdentityProvider() {
        return osIdentityProvider;
    }

    public void setOsIdentityProvider(String osIdentityProvider) {
        this.osIdentityProvider = osIdentityProvider;
    }

    public CredentialOS osProtocol(String osProtocol) {
        this.osProtocol = osProtocol;
        return this;
    }

    /**
     * example openid
     *
     * @return osProtocol
     *
     */
    public String getOsProtocol() {
        return osProtocol;
    }

    public void setOsProtocol(String osProtocol) {
        this.osProtocol = osProtocol;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CredentialOS credentialOS = (CredentialOS) o;
        return Objects.equals(this.osAuthUrl, credentialOS.osAuthUrl)
                && Objects.equals(this.osProjectId, credentialOS.osProjectId)
                && Objects.equals(this.osAuthType, credentialOS.osAuthType)
                && Objects.equals(this.osIdentityProvider, credentialOS.osIdentityProvider)
                && Objects.equals(this.osProtocol, credentialOS.osProtocol)
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(osAuthUrl, osProjectId, osAuthType, osIdentityProvider, osProtocol, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CredentialOS {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    osAuthUrl: ").append(toIndentedString(osAuthUrl)).append("\n");
        sb.append("    osProjectId: ").append(toIndentedString(osProjectId)).append("\n");
        sb.append("    osAuthType: ").append(toIndentedString(osAuthType)).append("\n");
        sb.append("    osIdentityProvider: ").append(toIndentedString(osIdentityProvider)).append("\n");
        sb.append("    osProtocol: ").append(toIndentedString(osProtocol)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
