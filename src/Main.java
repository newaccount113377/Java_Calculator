import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();                                        //считывание выражения из строки
        if(isValid(s)==1)
            System.out.print(toInt(s));
        else if(isValid(s)==2)
            if(toRome(s)<1)                                                   //проверка на то, положителен ли результат в случае ввода римских цифр
                System.out.print("Результат меньше 1, задача некорректна");
            else System.out.print(rome(toRome(s)));                           //преобразование результата из арабских цифр в римские
        else if(isValid(s)==0)
            System.out.print("Введено некорректное веражение");
    }

    public static int isValid(String s)                                       //функция проверяет, корректно ли введенное выражение
    {
        char[] s1=s.toCharArray();                                            //изменяемая копия считанной строки
        int[] a={0};                                                          //индекс, с которого нужно начинать считывание строки при каждом вызове функций isInt, isOperator и isRome
        if((isInt(s1, a)*isOperator(s1, a)*isInt(s1, a)>0)&&s1.length==a[0])  //проверка того, что выражение состоит только из двух чисел, записанных арабскими цифрами, и одного знака операции
        {
            return 1;
        }
        if((isRome(s1, a)*isOperator(s1, a)*isRome(s1, a)>0)&&s1.length==a[0])//проверка того, что выражение состоит только из двух чисел, записанных римскими цифрами, и одного знака операции
        {
            return 2;
        }
        return 0;                                                             //случай, когда считанное выражение некорректно
    }

    public static int isInt(char[] s, int[] a) {                              //функция считывает часть строки, начиная с индекса a[0], и проверяет, чтобы в данном участке строки
        int i = a[0];                                                         //были только символы арабских цифр, образующие одно число
        int n = 0;
        while (i != s.length) {
            if (Character.isDigit(s[i])) {
                n = n * 10 + Character.getNumericValue(s[i]);
                ++i;
            } else {
                break;
            }
        }
        if (i == a[0] || n > 10)                                              //если i=a[0], то в данном участке не было арабских цифр, т.е. выражение написано некорректно.
            return 0;                                                         //если n>10, то введенное выражение состоит как минимум из одного числа, большего 10, что не соответствует условию задачи
        return a[0]=i;                                                        //перемещение индекса начала считывания для следующей функции
    }

    public static int isOperator(char[] s, int[] a)                           //функция проверяет, чтобы между двумя числами был только один символ, означающий одну из 4 доступных операций
    {
        int i = a[0];
        while (i != s.length) {
            if (s[i]!='+'&s[i]!='-'&s[i]!='*'&s[i]!='/')
                break;
            ++i;
        }
        if (i!=a[0]+1)
            return 0;
        return a[0]=a[0]+1;                                                   //перемещение индекса начала считывания для следующей функции
    }

    public static boolean isRomeNumber(char c)                                //функция проверяет, что считанный символ может быть в составе римской записи числа в диапазоне от 1 до 10
    {
        if(c=='I'||c=='V'||c=='X')
            return true;
        return false;
    }

    public static int isRome(char[] s, int[] a)                               //функция считывает часть строки, начиная с индекса a[0], и проверяет, чтобы в данном участке строки
    {                                                                         //были только символы римских цифр, образующие одно число от 1 до 10
        int i = a[0];
        String s1="";
        while (i < s.length)
        {
            if (!isRomeNumber(s[i]))
                break;
            s1=s1+s[i];
            ++i;
        }
        a[0]=i;                                                               //перемещение индекса начала считывания для следующей функции
        if(s1.equals("I")||s1.equals("II")||s1.equals("III")||s1.equals("IV")||s1.equals("V")||s1.equals("VI")||s1.equals("VII")||s1.equals("VIII")||s1.equals("IX")||s1.equals("X"))
            return 1;                                                         //проверка, что считанные символы образуют существующую комбинацию
        return 0;
    }

    public static int toInt(String s)                                         //функция переводит части строки в соответствующие им значения int, после чего
    {                                                                         //производит над ними операцию, указанную в строке
        char[] s1=s.toCharArray();
        int a=toIntNumber(s1, 0);
        int b=toIntNumber(s1, a/10+2);
        if(s1[a/10+1]=='+'){
            return a+b;
        }
        if(s1[a/10+1]=='-'){
            return a-b;
        }
        if(s1[a/10+1]=='*'){
            return a*b;
        }
            return a/b;
    }

    public static int toIntNumber(char[] s, int a)                            //функция преобразует часть строки в соответсвующее ей значение int
    {
        int i=a;
        int n=0;
        while((i<s.length)&&(Character.isDigit(s[i])))
        {
            n = n * 10 + Character.getNumericValue(s[i]);
            ++i;
        }
        return n;
    }

    public static int toRome(String s)                                        //функция переводит части строки в соответствующие им значения int, после чего
    {                                                                         //производит над ними операцию, указанную в строке(случай с римской записью чисел)
        char[] s1=s.toCharArray();
        int[] c={0};
        int a=toRomeNumber(s1, c);
        ++c[0];
        if(s1[c[0]-1]=='+'){
            return a+toRomeNumber(s1, c);
        }
        if(s1[c[0]-1]=='-'){
            return a-toRomeNumber(s1, c);
        }
        if(s1[c[0]-1]=='*'){
            return a*toRomeNumber(s1, c);
        }
            return a/toRomeNumber(s1, c);
    }

    public static int toRomeNumber(char[] s, int[] a)                         //функция преобразует часть строки в соответсвующее ей значение int(случай с римской записью числа)
    {
        int i=a[0];
        int n=0;
        String s1="";
        while((i<s.length)&&isRomeNumber(s[i]))
        {
            s1=s1+s[i];
            ++i;
        }
        if(s1.equals("I")) {                                                  //сопоставление числу в римской записи соответсвующее ему значение int
            ++a[0];                                                           //смещение индекса, определяющего начало считывания строки для получения следующего числа
            return 1;
        }
        if(s1.equals("II")) {
            a[0]+=2;
            return 2;
        }
        if(s1.equals("III")) {
            a[0]+=3;
            return 3;
        }
        if(s1.equals("IV")) {
            a[0]+=2;
            return 4;
        }
        if(s1.equals("V")) {
            ++a[0];
            return 5;
        }
        if(s1.equals("VI")) {
            a[0]+=2;
            return 6;
        }
        if(s1.equals("VII")) {
            a[0]+=3;
            return 7;
        }
        if(s1.equals("VIII")) {
            a[0]+=4;
            return 8;
        }
        if(s1.equals("IX")) {
            a[0]+=2;
            return 9;
        }
        if(s1.equals("X")) {
            ++a[0];
            return 10;
        }
        return 0;
    }

    public static String rome(int a)                                          //функция переводит произвольное число от 1 до 100 из арабской записи в римскую
    {
        int n=a/10;                                                           //определение числа десятков в числе
        String s="";
        if(n<4)
        {
            for(int i=1; i<=n; ++i)
                s=s+"X";
        }
        else if(n==4)
            s="XL";
        else if(n<9)
        {
            s="L";
            for(int i=6; i<=n; ++i)
                s=s+"X";
        }
        else if(n==9)
            s="XC";
        else if(n==10)
            s="C";
        n=a-10*n;                                                             //определение числа оставшихся единиц
        if(n<4)
        {
            for(int i=1; i<=n; ++i)
                s=s+"I";
        }
        else if(n==4)
            s=s+"IV";
        else if(n<9)
        {
            s=s+"V";
            for(int i=6; i<=n; ++i)
                s=s+"I";
        }
        else if(n==9)
            s=s+"IX";
        else if(n==10)
            s=s+"X";
        return s;
    }
}