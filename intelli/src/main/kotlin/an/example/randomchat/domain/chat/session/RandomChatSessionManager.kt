package an.example.randomchat.domain.chat.session

import an.example.randomchat.domain.user.User
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class RandomChatSessionManager {
    //큰 틀에서의 세션의 이해, 쿠키와 비슷한 작동을 함
    //즉, 클라이언트가 웹서버에 접속해 있는 상태를 하나의 단위로 보고 이를 세션이라 한다.
    //일정 시간동안 같은 사용자(브라우저)에게서 들어오는 일련의 요구들을 하나의 상태라 판단하고 이 상태를 유지시키는 기술인데 채팅에 필수적이여보인다.
    //쿠키와 다르게 클라이언트의 메모리가 아닌 웹서버에서 세션아이디를 이용하여 서버에 저장한다.
    //세션의 종류에 따라 이용이나 동작 순서가 다른듯 하다. ex)http session

    private val sessions = ConcurrentHashMap<User, RandomChatSession>()

    fun addSession(user: User, session: RandomChatSession){
        sessions[user] = session
    }

    fun removeSession(user: User){
        sessions[user]?.close()
        sessions.remove(user)
    }

    fun getSession(user: User):RandomChatSession? {
        return sessions[user]
    }
}