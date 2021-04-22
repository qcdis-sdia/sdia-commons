package nl.uva.qcdis.sdia.model.tosca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import org.springframework.data.annotation.Id;

/**
 * Credential
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-10T15:39:04.296Z")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credential {

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
  
    @Id
    @JsonIgnore
    private String id;

    @JsonProperty("protocol")
    private String protocol = null;

    @JsonProperty("token_type")
    private String tokenType = null;

    @JsonProperty("token")
    private String token = null;

    @JsonProperty("keys")
    @Valid
    private Map<String, String> keys = null;
    
    @JsonProperty("extra_properties")
    @Valid
    private Map<String, String> extraProperties = null;

    @JsonProperty("user")
    private String user = null;

    @JsonProperty("cloud_provider_name")
    private String cloudProviderName = null;

    @JsonProperty("auth_url")
    private String authUrl = null;

    @JsonProperty("project_id")
    private String projectId = null;

    @JsonProperty("identity_provider")
    private String identityProvider = null;
    
    @JsonProperty("auth_type")
    private String authType = null;
    

    public Credential protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Get protocol
     *
     * @return protocol
     *
     */
    @ApiModelProperty(value = "")

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Credential authUrl(String authUrl) {
        this.authUrl = authUrl;
        return this;
    }

    /**
     * the Keystone url. example https://stack-server.ct.infn.it:35357/v3
     *
     * @return authUrl
     *
     */
    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public Credential projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * the project example 745695ccd17042fabf96d2410a4278d9
     *
     * @return projectId
     *
     */
    public String getProjectId() {
        return projectId;
    }

    public void setOsProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Credential tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    /**
     * Get tokenType
     *
     * @return tokenType
     *
     */
    @ApiModelProperty(value = "")

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Credential token(String token) {
        this.token = token;
        return this;
    }

    public Credential authType(String authType) {
        this.authType = authType;
        return this;
    }

    /**
     * example egi.eu
     *
     * @return authType
     *
     */
    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

      public Credential identityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
        return this;
    }

    /**
     * example egi.eu
     *
     * @return identityProvider
     *
     */
    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    
    /**
     * Get token
     *
     * @return token
     *
     */
    @ApiModelProperty(value = "")

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Credential extraProperties(Map<String, String> keys) {
        this.extraProperties = extraProperties;
        return this;
    }


    /**
     * Get keys
     *
     * @return keys
     *
     */
    @ApiModelProperty(value = "")

    public Map<String, String> getExtraProperties() {
        return extraProperties;
    }

    public void setExtraProperties(Map<String, String> extraProperties) {
        this.extraProperties = extraProperties;
    }
    
    public Credential keys(Map<String, String> keys) {
        this.keys = keys;
        return this;
    }

    public Credential putKeysItem(String key, String keysItem) {
        if (this.keys == null) {
            this.keys = new HashMap<>();
        }
        this.keys.put(key, keysItem);
        return this;
    }

    /**
     * Get keys
     *
     * @return keys
     *
     */
    @ApiModelProperty(value = "")

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }    

    public Credential user(String user) {
        this.user = user;
        return this;
    }

    /**
     * Get user
     *
     * @return user
     *
     */
    @ApiModelProperty(value = "")

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Credential cloudProviderName(String cloudProviderName) {
        this.cloudProviderName = cloudProviderName;
        return this;
    }

    /**
     * Get cloudProviderName
     *
     * @return cloudProviderName
     *
     */
    @ApiModelProperty(value = "")

    public String getCloudProviderName() {
        return cloudProviderName;
    }

    public void setCloudProviderName(String cloudProviderName) {
        this.cloudProviderName = cloudProviderName;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Credential credentials = (Credential) o;
        return Objects.equals(this.protocol, credentials.protocol)
                && Objects.equals(this.tokenType, credentials.tokenType)
                && Objects.equals(this.token, credentials.token)
                && Objects.equals(this.keys, credentials.keys)
                && Objects.equals(this.extraProperties, credentials.extraProperties)
                && Objects.equals(this.user, credentials.user)
                && Objects.equals(this.identityProvider, credentials.identityProvider)
                && Objects.equals(this.authType, credentials.authType)
                && Objects.equals(this.projectId, credentials.projectId)
                && Objects.equals(this.authUrl, credentials.authUrl)
                && Objects.equals(this.cloudProviderName, credentials.cloudProviderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, tokenType, token, keys, user, cloudProviderName,
                 authUrl, projectId, identityProvider,authType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Credentials {\n");

        sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
        sb.append("    tokenType: ").append(toIndentedString(tokenType)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("    keys: ").append(toIndentedString(keys)).append("\n");
        sb.append("    extraProperties: ").append(toIndentedString(extraProperties)).append("\n");
        sb.append("    user: ").append(toIndentedString(user)).append("\n");
        sb.append("    identityProvider: ").append(toIndentedString(identityProvider)).append("\n");
        sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
        sb.append("    osProjectId: ").append(toIndentedString(projectId)).append("\n");
        sb.append("    cloudProviderName: ").append(toIndentedString(cloudProviderName)).append("\n");
        sb.append("    osAuthUrl: ").append(toIndentedString(authUrl)).append("\n");
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
