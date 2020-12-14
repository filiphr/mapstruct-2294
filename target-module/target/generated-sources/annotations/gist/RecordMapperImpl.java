package gist;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-07T08:40:43+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
public class RecordMapperImpl implements RecordMapper {

    @Override
    public TargetRecord mapWorks(SourceInSamePackage source) {
        if ( source == null ) {
            return null;
        }

        List<String> data = null;

        List<String> list = source.data();
        if ( list != null ) {
            data = new ArrayList<String>( list );
        }

        TargetRecord targetRecord = new TargetRecord( data );

        return targetRecord;
    }
}
