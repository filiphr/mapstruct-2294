package gist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import source.SourceInAnotherPackage;

@Mapper
public interface RecordMapper {

    TargetRecord mapWorks(SourceInSamePackage source);

    TargetRecord mapDoesntWorkToo(SourceInAnotherPackage source);
}
