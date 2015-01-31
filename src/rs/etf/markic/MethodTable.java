package rs.etf.markic;

import java.util.List;
import java.util.ArrayList;

import rs.etf.pp1.mj.runtime.Code;

/**
*
* @author Marin Markic
* 
*/

public class MethodTable {

	// virtual functions table content
	private List<Byte> methodTable = new ArrayList<Byte>();
	
	public MethodTable() {}
	
	void addWordToStaticData(int value, int address)
	{
		methodTable.add(new Byte( (byte)Code.const_));
		methodTable.add(new Byte( (byte)( (value>>16)>>8 ) ) ); 
		methodTable.add(new Byte( (byte)(value>>16))); 
		methodTable.add(new Byte( (byte)(value>>8))); 
		methodTable.add(new Byte( (byte)value)); 
		methodTable.add(new Byte( (byte)Code.putstatic)); 
		methodTable.add(new Byte( (byte)(address>>8))); 
		methodTable.add(new Byte( (byte)address));
	}
	
	// virtual method name terminator
	void addNameTerminator() 
	{ 
		addWordToStaticData(-1, Code.dataSize++); 
	}
	
	// virtual functions table terminator
	void addTableTerminator() 
	{ 
		addWordToStaticData(-2, Code.dataSize++); 
	} 
	
	void addFunctionAddress(int functionAddress) 
	{ 
		addWordToStaticData(functionAddress, Code.dataSize++); 
	} 
	
	void addFunctionEntry(String name, int functionAddressInCodeBuffer) 
	{ 
		for (int j=0; j<name.length(); j++) 
		{ 
			addWordToStaticData((int)(name.charAt(j)), Code.dataSize++); 
		} 
		
		addNameTerminator(); 
		addFunctionAddress(functionAddressInCodeBuffer); 
	}
	
	Object [] toArray()
	{
		return methodTable.toArray();
	}
	
	void clear()
	{
		methodTable.clear();
	}

}
