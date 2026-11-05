
.assembly extern mscorlib {}
.assembly _programa {}
.module _programa.exe

.class public _unica {
    .method static public void _principal() {
        .entrypoint

        .locals init (int64 i_base, int64 i_altura, float64 f_area)

        ldstr "Digite a base:"
        call void [mscorlib]System.Console::WriteLine(string)
        call string [mscorlib]System.Console::ReadLine()
        call int64 [mscorlib]System.Int64::Parse(string)
        stloc i_base
        
        ldstr "Digite a altura:"
        call void [mscorlib]System.Console::WriteLine(string)
        call string [mscorlib]System.Console::ReadLine()
        call int64 [mscorlib]System.Int64::Parse(string)
        stloc i_altura

        ldloc i_base
        conv.r8
        ldloc i_altura
        conv.r8
        mul
        ldc.r8 2.0
        div
        stloc f_area
        
        ldloc f_area
        call void [mscorlib]System.Console::WriteLine(float64)    
    
        ret
    }
}
