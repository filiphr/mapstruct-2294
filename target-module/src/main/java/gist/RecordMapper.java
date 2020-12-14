package gist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import source.SourceInAnotherPackage;


@Mapper
public interface RecordMapper {

    TargetRecord mapWorks(SourceInSamePackage source);

    TargetRecord mapDoesntWork(SourceInAnotherPackage source);

    @Mapping(target = "data", source = "data")
    TargetRecord mapDoesntWorkToo(SourceInAnotherPackage source);
}
