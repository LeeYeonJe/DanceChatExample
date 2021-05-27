package an.example.randomchat.domain.user

import java.util.concurrent.atomic.AtomicLong

class User private constructor(
    val id: Long,
    val nickName: String
){
    companion object{
        private val nextUserId = AtomicLong(1)//DB에 저장하지 않기 때문에 sync없이 관리하기 위해 이용함

        fun create(nickName: String): User {
            val userId = nextUserId.getAndIncrement()//리턴하고 1증가
            return User(userId, nickName)
        }
    }
}