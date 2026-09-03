package compilador.lexico;
public interface Constants extends ScannerConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_palavra = 2;
    int t_TOKEN_3 = 3; //","
    int t_TOKEN_4 = 4; //":"
    int t_TOKEN_5 = 5; //";"
    int t_TOKEN_6 = 6; //"[]"
    int t_TOKEN_7 = 7; //"()"
    int t_TOKEN_8 = 8; //"{}"
    int t_TOKEN_9 = 9; //"+"
    int t_TOKEN_10 = 10; //"-"
    int t_TOKEN_11 = 11; //"*"
    int t_TOKEN_12 = 12; //"/"
    int t_TOKEN_13 = 13; //"<-"
    int t_TOKEN_14 = 14; //"="
    int t_TOKEN_15 = 15; //"<>"
    int t_TOKEN_16 = 16; //"<"
    int t_TOKEN_17 = 17; //">"
    int t_and = 18;
    int t_false = 19;
    int t_if = 20;
    int t_in = 21;
    int t_isFalseDo = 22;
    int t_isTrueDo = 23;
    int t_module = 24;
    int t_not = 25;
    int t_or = 26;
    int t_out = 27;
    int t_true = 28;
    int t_while = 29;
    int t_id_int = 30;
    int t_id_float = 31;
    int t_id_string = 32;
    int t_id_bool = 33;
    int t_cte_int = 34;
    int t_cte_float = 35;
    int t_cte_string = 36;

}
