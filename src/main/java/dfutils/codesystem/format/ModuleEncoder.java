package dfutils.codesystem.format;

import dfutils.codesystem.objects.CodeModule;

public interface ModuleEncoder<T> {
	
	T encodeModule(CodeModule codeModule);
}
