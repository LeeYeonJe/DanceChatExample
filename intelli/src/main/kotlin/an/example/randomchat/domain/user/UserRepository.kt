package an.example.randomchat.domain.user

import an.example.randomchat.common.RandomChatException
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {//jpareposi 상속안함 = DB안쓰고 메모리에서만 작동

    private val users = mutableListOf<User>()


    private val indexById = ConcurrentHashMap<Long, User>() //threadsafety때문에 이용함 + hashtable과 다른 점 존재함 나중에 공부!
    private val indexByNickName = ConcurrentHashMap<String, User>()

    fun create(nickName: String): User {
        validate(nickName)
        return createUser(nickName)
    }

    private fun validate(nickName: String){
        findByNickName(nickName)?.let{
            throw RandomChatException("이미사용중닉네임")
        }
    }

    private fun createUser(nickName: String): User{
        val user = User.create(nickName)

        users.add(user)
        onCreateUser(user)
        return(user)
    }

    private fun onCreateUser(user: User){
        indexById[user.id] = user
        indexByNickName[user.nickName] = user
    }//생성된 유저를 맵에 등록하여서 검색을 빠르게 함

    fun findByNickName(nickName: String): User? {
        return indexByNickName[nickName]
    }

    fun findById(id: Long): User?{
        return indexById[id]
    }

    fun deleteUser(user: User){
        findById(user.id)?.let {
            users.remove(it)
            onDeleteUser(it)
        }
    }

    private fun onDeleteUser(user: User){
        indexById.remove(user.id)
        indexByNickName.remove(user.nickName)
    }//유저 삭제 이후 맵에서도 삭제 해줌
}