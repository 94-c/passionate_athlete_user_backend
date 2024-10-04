package com.backend.athlete.user.branch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBranch is a Querydsl query type for Branch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBranch extends EntityPathBase<Branch> {

    private static final long serialVersionUID = 1263533594L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBranch branch = new QBranch("branch");

    public final com.backend.athlete.support.common.QBaseTimeEntity _super = new com.backend.athlete.support.common.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final StringPath detailedAddress = createString("detailedAddress");

    public final StringPath etc = createString("etc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.backend.athlete.user.user.QUser manager;

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath postalCode = createString("postalCode");

    public final SetPath<com.backend.athlete.user.user.User, com.backend.athlete.user.user.QUser> users = this.<com.backend.athlete.user.user.User, com.backend.athlete.user.user.QUser>createSet("users", com.backend.athlete.user.user.User.class, com.backend.athlete.user.user.QUser.class, PathInits.DIRECT2);

    public QBranch(String variable) {
        this(Branch.class, forVariable(variable), INITS);
    }

    public QBranch(Path<? extends Branch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBranch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBranch(PathMetadata metadata, PathInits inits) {
        this(Branch.class, metadata, inits);
    }

    public QBranch(Class<? extends Branch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manager = inits.isInitialized("manager") ? new com.backend.athlete.user.user.QUser(forProperty("manager"), inits.get("manager")) : null;
    }

}

