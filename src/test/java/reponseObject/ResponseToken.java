package reponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseToken {

    @JsonProperty("token")
    private String token;

    @JsonProperty("expires")
    private String expires;

    @JsonProperty("status")
    private String status;

    @JsonProperty("result")
    private String result;
}
