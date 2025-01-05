package pt.psoft.g1.psoftg1.common;

public enum EventType {

    BOOT_USERS,
    BOOT_BOOKS,
    BOOT_AUTHORS,
    BOOT_GENRES,
    BOOT_LENDINGS,

    USER_CREATE,
    USER_DELETE,
    USER_UPDATE,

    READER_CREATE,
    READER_DELETE,
    READER_UPDATE,


    BOOK_CREATE,
    BOOK_DELETE,
    BOOK_UPDATE,

    AUTHOR_CREATE,
    AUTHOR_DELETE,

    GENRE_CREATE,
    GENRE_DELETE,

    BOOK_SUGGEST,
    BOOK_RECOMMEND,
    BOOK_RETURN,

    LENDING_CREATE,
    LENDING_DELETE,
    LENDING_UPDATE,
    LENDING_RECOMMEND,

    FINE_CREATE,
    FINE_DELETE,
    FINE_UPDATE,

    ERROR

}
