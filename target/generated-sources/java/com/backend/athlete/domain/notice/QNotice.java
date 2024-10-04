package com.backend.athlete.user.notice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = -2036443814L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotice notice = new QNotice("notice");

    public final com.backend.athlete.support.common.QBaseTimeEntity _super = new com.backend.athlete.support.common.QBaseTimeEntity(this);

    public final ListPath<com.backend.athlete.user.comment.Comment, com.backend.athlete.user.comment.QComment> comments = this.<com.backend.athlete.user.comment.Comment, com.backend.athlete.user.comment.QComment>createList("comments", com.backend.athlete.user.comment.Comment.class, com.backend.athlete.user.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final NumberPath<Integer> kind = createNumber("kind", Integer.class);

    public final ListPath<Like, QLike> likes = this.<Like, QLike>createList("likes", Like.class, QLike.class, PathInits.DIRECT2);

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final BooleanPath status = createBoolean("status");

    public final StringPath title = createString("title");

    public final com.backend.athlete.user.user.QUser user;

    public QNotice(String variable) {
        this(Notice.class, forVariable(variable), INITS);
    }

    public QNotice(Path<? extends Notice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotice(PathMetadata metadata, PathInits inits) {
        this(Notice.class, metadata, inits);
    }

    public QNotice(Class<? extends Notice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.backend.athlete.user.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

