package std;
import mode.*;
import stereotype.*;

/**
 * @tsl <A HREF= "emos_std_registry_lib_tsl.html" target=tsl>emos_std_registry_lib.tsl</A>
 */
public class emos_std_registry_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_registry_lib (){}
protected String loadRegistry(  ) {}

public String unloadRegistry(  ) {}

public String OpenRegistryKey( in root, in key ) {}

public extern._long GetReservedKey( in._int root ) {}

public extern._long GetSubKey( in._long hKey, in._string subKey ) {}

public extern._string GetValueData( in._long hKey, in._string subKey, in._string value ) {}

public extern._int SetValueData( in._long hKey, in._string subKey, in._string value, in._string data ) {}

}
