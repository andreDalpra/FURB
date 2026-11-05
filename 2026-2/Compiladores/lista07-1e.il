
.assembly extern mscorlib {}
.assembly _programa {}
.module _programa.exe

.class public _unica {
    .method static public void _principal() {
        .entrypoint

        .locals init (int64 i_cont, int64 i_n, int64 i_valor)

        ldc.i8 1
        stloc i_cont
        
        ldstr "digite um int:"
        call void [mscorlib]System.Console::WriteLine(string)
        call string [mscorlib]System.Console::ReadLine()
        call int64 [mscorlib]System.Int64::Parse(string)
        stloc i_n
        
        WHILE:
            // Ve se é menor que 10
            ldloc i_cont
            ldc.i8 10
            clt
            brfalse END // exit
        
            ldloc i_n
            ldloc i_cont
            mul
            stloc i_valor
            
            // Printa
            ldloc i_valor
            call void [mscorlib]System.Console::Write(int64)
            ldstr "   "
            call void [mscorlib]System.Console::Write(string)
            
            // Incrementa
            ldc.i8 1
            ldloc i_cont
            add
            stloc i_cont                    
            br WHILE    
        END:
        ret
    }
}