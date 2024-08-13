package com.backend.athlete.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1266622650L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.backend.athlete.support.common.QBaseTimeEntity _super = new com.backend.athlete.support.common.QBaseTimeEntity(this);

    public final com.backend.athlete.domain.branch.QBranch branch;

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final EnumPath<com.backend.athlete.domain.user.domain.type.UserGenderType> gender = createEnum("gender", com.backend.athlete.domain.user.domain.type.UserGenderType.class);

    public final NumberPath<Double> height = createNumber("height", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final SetPath<Role, QRole> roles = this.<Role, QRole>createSet("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final EnumPath<com.backend.athlete.domain.user.domain.type.UserStatusType> status = createEnum("status", com.backend.athlete.domain.user.domain.type.UserStatusType.class);

    public final StringPath userId = createString("userId");

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branch = inits.isInitialized("branch") ? new com.backend.athlete.domain.branch.QBranch(forProperty("branch"), inits.get("branch")) : null;
    }

}

