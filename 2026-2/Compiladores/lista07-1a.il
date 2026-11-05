
.assembly extern mscorlib {}
.assembly _programa {}
.module _programa.exe

.class public _unica {
    .method static public void _principal() {
        .entrypoint

            // i_lado, i_area
            .locals init (int64 i_lado, int64 i_area)

        // in (i_lado)
        call string [mscorlib]System.Console::ReadLine()
        call int64 [mscorlib]System.Int64::Parse(string)
        stloc i_lado

        // i_area <- i_lado * i_lado
        ldloc i_lado
        conv.r8
        ldloc i_lado
        conv.r8
        mul
        stloc i_area

        // out (i_area)
        ldloc i_area
        conv.r8
        conv.i8
        call void [mscorlib]System.Console::WriteLine(int64)

        ret
    }
}
