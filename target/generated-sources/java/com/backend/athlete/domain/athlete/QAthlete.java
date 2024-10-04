package com.backend.athlete.user.workout;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAthlete is a Querydsl query type for Athlete
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAthlete extends EntityPathBase<Athlete> {

    private static final long serialVersionUID = -239321092L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAthlete athlete = new QAthlete("athlete");

    public final StringPath athletics = createString("athletics");

    public final DatePath<java.time.LocalDate> dailyTime = createDate("dailyTime", java.time.LocalDate.class);

    public final StringPath etc = createString("etc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TimePath<java.time.LocalTime> record = createTime("record", java.time.LocalTime.class);

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final EnumPath<com.backend.athlete.user.athlete.type.AthleteSuccessType> type = createEnum("type", com.backend.athlete.user.athlete.type.AthleteSuccessType.class);

    public final com.backend.athlete.user.user.QUser user;

    public QAthlete(String variable) {
        this(Athlete.class, forVariable(variable), INITS);
    }

    public QAthlete(Path<? extends Athlete> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAthlete(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAthlete(PathMetadata metadata, PathInits inits) {
        this(Athlete.class, metadata, inits);
    }

    public QAthlete(Class<? extends Athlete> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.backend.athlete.user.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

