package cc.worldmandia.moderURLShortener

class NotFoundException(id: Long) : Exception("Could not find entity with $id id")
class AlreadyExistException(id: Long) : Exception("Entity with $id id already exists")