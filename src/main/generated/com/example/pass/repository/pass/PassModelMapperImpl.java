package com.example.pass.repository.pass;

import org.mapstruct.Mapping;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-19T23:08:57+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
public class PassModelMapperImpl implements PassModelMapper {

    @Mapping(target = "remainingCount", source = "bulkPassEntity.count")
    @Override
    public PassEntity toPassEntity(BulkPassEntity bulkPassEntity, String userId) {
        if ( bulkPassEntity == null && userId == null ) {
            return null;
        }

        PassEntity.PassEntityBuilder passEntity = PassEntity.builder();

        if ( bulkPassEntity != null ) {
            passEntity.status( status( bulkPassEntity.getStatus() ) );
            passEntity.remainingCount( bulkPassEntity.getCount() );
            passEntity.packageSeq( bulkPassEntity.getPackageSeq() );
            passEntity.startedAt( bulkPassEntity.getStartedAt() );
            passEntity.endedAt( bulkPassEntity.getEndedAt() );
        }
        passEntity.userId( userId );

        return passEntity.build();
    }
}
