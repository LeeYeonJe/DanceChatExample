package an.example.randomchat.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*


object JWTUtil {

    private const val  ISSUER = "DanceChat"
    private const val  SUBJECT = "DanceChatToken"
    private const val  EXPIRE_TIME = 60L * 60 * 2 * 1000
    private const val  REFRESH_EXPIRE_TIME = 60L * 60 * 24 * 30 * 1000

    private val secret = "dlduswp12"//해당 비밀번호로 유효토큰생성가능
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)

    private val refreshSecret = "dlduswp12re"
    private val refreshAlgorithm : Algorithm = Algorithm.HMAC256(refreshSecret)

    fun createToken(nickName: String, userId: Long) = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + EXPIRE_TIME))
        .withClaim(JWTClaims.NICK_NAME, nickName)
        .withClaim(JWTClaims.USER_ID, userId)//사용자설정에해당하는부분
        .sign(algorithm)

    fun createRefreshToken(nickName: String, userId: Long) = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
        .withClaim(JWTClaims.NICK_NAME, nickName)
        .withClaim(JWTClaims.USER_ID, userId)
        .sign(refreshAlgorithm)

    fun verify(token: String): DecodedJWT =
        JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun verifyRefresh(token: String): DecodedJWT =
        JWT.require(refreshAlgorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun extractNickName(jwt: DecodedJWT): String =
        jwt.getClaim(JWTClaims.NICK_NAME).asString()

    fun extrackId(jwt: DecodedJWT): Long =
        jwt.getClaim(JWTClaims.USER_ID).asLong()


    object JWTClaims{
        const val NICK_NAME = "nickName"
        const val USER_ID = "userId"
    }
}