package com.example.demo.Util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ApiUtils {

    // 보낼 때의 형태를 지정하는 것
    //<T> 랑 ApiResult<T> 가 다른데
    // 같다고 생각하고 사용?

    public static <T> ApiResult<T> success(T response){
        return new ApiResult<T>(true,response, null);
    }

    public static <T> ApiResult<T> error (String message){
        return new ApiResult<T>(false,null, new ApiError(message));
        // 밑에서 만든ㄷ 메세지를 반환
    }

    // 리스폰스는 당연히 널이어야 하고
    // 에러가 널 이 아니라
    // 에러는 성공을 못했으니까 반환 값이 없어야 함 -> 메세지를 갖고 옴


    // ** JSON으로 반환해야 할 데이터
    @AllArgsConstructor @Setter @Getter
    public static class ApiResult<T>{
        private final boolean success; // 현재 상태
        private final T response; // 반환할 실제 데이터
        private final ApiError error;
        /*
        public ApiResult (boolean success,T response, ApiError error){
            this.success = success;
            this.response = response;
            this.error = error;
        }
         */
        public String toString(){
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("success", success)
                    .append("response", response)
                    .append("error", error)
                    .toString();
        }

    }
    // 스트링으로 변경해주는 빌더 패턴
    // 제이슨을 끝으로 몰아줌


    @AllArgsConstructor @Setter @Getter
    public static class ApiError{
        private String message;
        /*
        public ApiError(String message){
            this.message = message;
        }
         */
        public String toString(){
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("message", message)
                    .toString();
        }
    }
}
