package springbootstarter.entities;

public class JwtProperties {
    public static final String SECRET="MYSE";
    public static final int EXPIRATION_TIME=864000000; //equivalent to 10 days
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
}
