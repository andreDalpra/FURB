
.assembly extern mscorlib {}
.assembly _programa {}
.module _programa.exe

.class public _unica {
    .method static public void _principal() {
        .entrypoint

        .locals init (float64 f_numero)

        ldstr "Digite um float:"
        call void [mscorlib]System.Console::WriteLine(string)
        call string [mscorlib]System.Console::ReadLine()
        call float64 [mscorlib]System.Double::Parse(string)
        stloc f_numero

        ldloc f_numero
        ldc.r8 0.0
        cgt
        brtrue IF
        // ELSE
            ldstr "menor do que 0"
            call void [mscorlib]System.Console::WriteLine(string)
            br END
                
        IF:
            ldstr "maior do que 0"
            call void [mscorlib]System.Console::WriteLine(string)
                  
        END:
        ret
    }
}
