package com.backend.athlete.domain.physical;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhysical is a Querydsl query type for Physical
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhysical extends EntityPathBase<Physical> {

    private static final long serialVersionUID = 1192483002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhysical physical = new QPhysical("physical");

    public final com.backend.athlete.support.common.QBaseTimeEntity _super = new com.backend.athlete.support.common.QBaseTimeEntity(this);

    public final NumberPath<Double> bmi = createNumber("bmi", Double.class);

    public final NumberPath<Double> bmr = createNumber("bmr", Double.class);

    public final NumberPath<Double> bodyFatMass = createNumber("bodyFatMass", Double.class);

    public final NumberPath<Double> bodyFatPercentage = createNumber("bodyFatPercentage", Double.class);

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final NumberPath<Double> height = createNumber("height", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> measureDate = createDate("measureDate", java.time.LocalDate.class);

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final NumberPath<Double> muscleMass = createNumber("muscleMass", Double.class);

    public final com.backend.athlete.domain.user.QUser user;

    public final NumberPath<Double> visceralFatPercentage = createNumber("visceralFatPercentage", Double.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QPhysical(String variable) {
        this(Physical.class, forVariable(variable), INITS);
    }

    public QPhysical(Path<? extends Physical> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhysical(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhysical(PathMetadata metadata, PathInits inits) {
        this(Physical.class, metadata, inits);
    }

    public QPhysical(Class<? extends Physical> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.backend.athlete.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

