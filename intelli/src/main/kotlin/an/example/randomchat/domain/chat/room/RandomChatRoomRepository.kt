package an.example.randomchat.domain.chat.room

import an.example.randomchat.domain.user.User
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class RandomChatRoomRepository {

    private val rooms = mutableListOf<RandomChatRoom>()//채팅방리스트를 담고있을 프로퍼티

    private val indexByUser = ConcurrentHashMap<User,RandomChatRoom>()//채팅 서비스 형태에 따라 인덱스 정의 방법 달라져야함

    fun create(user1: User, user2: User): RandomChatRoom{
        val room = RandomChatRoom
            .create()
            .also {
                it.addUser(user1)
                it.addUser(user2)
            }
        synchronized(rooms){
            rooms.add(room)
        }
        onUserAddedToRoom(user1,room)
        onUserAddedToRoom(user2,room)

        return room
    }

    fun remove(room: RandomChatRoom){
        synchronized(rooms){
            rooms.remove(room)
        }

        onRemoveRoom(room)
    }

    fun findByUser(user: User): RandomChatRoom? {
        return indexByUser[user]
    }

    fun addUserToRoom(room: RandomChatRoom, user: User){
        room.addUser(user)
        onUserAddedToRoom(user,room)
    }

    fun removeUserFromRoom(user: User){
        synchronized(indexByUser){
            indexByUser[user]?.let { room ->
                room.removeUser(user)
                onUserRemovedFromRoom(user)
            }
        }
    }

    private fun onUserAddedToRoom(user:User, room: RandomChatRoom){
        indexByUser[user] = room
    }


    private fun onRemoveRoom(room: RandomChatRoom){
        synchronized(indexByUser){
            room.users
                .forEach { user ->
                    onUserRemovedFromRoom(user)
                }
        }
    }

    private fun onUserRemovedFromRoom(user:User){
        synchronized(indexByUser){
            indexByUser[user]?.let {
                room -> room.removeUser(user)
                onUserRemovedFromRoom(user)
            }
        }
    }




}