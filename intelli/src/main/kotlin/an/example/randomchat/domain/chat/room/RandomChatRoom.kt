package an.example.randomchat.domain.chat.room

import an.example.randomchat.domain.user.User
import java.util.concurrent.atomic.AtomicLong

class RandomChatRoom private constructor(
    val id: Long
){

    val users = mutableListOf<User>()//1:1이 아니라 다수 채팅 확장성 고려

    fun addUser(user: User){
        synchronized(users){//mutablelist가 threadsafe하지않아서 이용함함
           users.add(user)
        }
    }

    fun removeUser(user: User){
        synchronized(users){
            users.remove(user)
        }
    }

    companion object {
        private val nextRoomId = AtomicLong(1)

        fun create(): RandomChatRoom{
            val roomId = nextRoomId.getAndIncrement()
            return RandomChatRoom(roomId)//채팅방은 하나씩 생성되어야 하므로 atomiclong을 이용한다는점 유의!ㅡ 우리의 경우 채팅방을 다시 접속해야할텐데 다른 방법 찾아봐야함
            //참조 : https://okky.kr/article/459448
        }
    }
}