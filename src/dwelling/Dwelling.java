package dwelling;

import interfaces.Floor;

import java.util.Arrays;

public class Dwelling{
    private Floor[] floors;

    public Dwelling( int... spacesCountOnEachFloor ){
        this( Arrays.stream( spacesCountOnEachFloor ).mapToObj( DwellingFloor::new )
                    .toArray( value -> new Floor[ spacesCountOnEachFloor.length ] ) );
    }

    public Dwelling( Floor[] floors ){
        this.floors = floors;
    }
}
