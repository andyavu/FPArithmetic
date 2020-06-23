package cs3650hw3;

public class fp
{
    public String myName()
    {
        return "Andy Vu";
    }

    public int add(int a, int b)
    {
        FPNumber fa = new FPNumber(a);
        FPNumber fb = new FPNumber(b);
        FPNumber result = new FPNumber(0);   
/***********************************************
  1. Handle exception values
***********************************************/
        // if either input NaN, return NaN input
        if(fa.isNaN() || fb.isNaN())
        {
            if(fa.isNaN())
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(fa.f());
            }
            else if(fb.isNaN())
            {
                result.setS(fb.s());
                result.setE(fb.e());
                result.setF(fb.f());
            }
            return result.asInt();
        }
        // if either input 0, return other input
        else if(fa.isZero() || fb.isZero())
        {
            if(fa.isZero())
            {
                result.setS(fb.s());
                result.setE(fb.e());
                result.setF(fb.f());
            }
            else if(fb.isZero())
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(fa.f());
            }
            return result.asInt();
        }
        // if both inputs infinity
        else if(fa.isInfinity() && fb.isInfinity())
        {
            // if signs of inputs same, return any input
            if(fa.s() == fb.s())
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(fa.f());
            }
            // if signs of inputs different, return NaN of either sign
            else
            {
                result.setS(1);
                result.setE(255);
                result.setF(12);
            }
            return result.asInt();
        }
        // if either input infinity, return infinity input
        else if(fa.isInfinity() || fb.isInfinity())
        {
            if(fa.isInfinity())
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(fa.f());
            }
            else if(fb.isInfinity())
            {
                result.setS(fb.s());
                result.setE(fb.e());
                result.setF(fb.f());
            }
            return result.asInt();
        }
        
        long t = 0;
/***********************************************
  2. Sort numbers
***********************************************/
        // a > b
        if(a > b)
        {        
/***********************************************
  3. Align exponents
***********************************************/
            int shift = fa.e() - fb.e();
            
            if(shift > 24)
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(fa.f());
                return result.asInt();
            }
/***********************************************
  4. Add or subtract
***********************************************/
            else if(fa.s() == fb.s())
            {
                t = fa.f() + (fb.f() >> shift);
            }
            else
            {
                t = fa.f() - (fb.f() >> shift);
                
                if(t == 0)
                {
                    result.setS(fa.s());
                    result.setE(0);
                    result.setF(0);
                    return result.asInt();
                }
            }
/***********************************************
  5. Normalize and round
***********************************************/
            if (((t >> 26) & 1) == 1)
            {
                if(fa.e() + 1 == 255)
                {
                    result.setS(fa.s());
                    result.setE(255);
                    result.setF(0);
                    return result.asInt();
                }
                else
                {
                    result.setS(fa.s());
                    result.setE(fa.e() + 1);
                    t = t >> 1;
                    result.setF(t);
                }
            }
            else
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(t);
            }
        }
        
        // a < b
        else
        {
/***********************************************
  3. Align exponents
***********************************************/
            int shift = fb.e() - fa.e();
            
            if(shift > 24)
            {
                result.setS(fb.s());
                result.setE(fb.e());
                result.setF(fb.f());
                return result.asInt();
            }
/***********************************************
  4. Add or subtract
***********************************************/
            else if(fb.s() == fa.s())
            {
                t = fb.f() + (fa.f() >> shift);
            }
            else
            {
                t = fb.f() - (fa.f() >> shift);
                
                if(t == 0)
                {
                    result.setS(fb.s());
                    result.setE(0);
                    result.setF(0);
                    return result.asInt();
                }
            }
/***********************************************
  5. Normalize and round
***********************************************/
            if (((t >> 26) & 1) == 1)
            {
                if(fb.e() + 1 == 255)
                {
                    result.setS(fb.s());
                    result.setE(255);
                    result.setF(0);
                    return result.asInt();
                }
                else
                {
                    result.setS(fb.s());
                    result.setE(fb.e() + 1);
                    t = t >> 1;
                    result.setF(t);
                }
            }
            else
            {
                result.setS(fb.s());
                result.setE(fb.e());
                result.setF(t);
            }
        }
        return result.asInt();
    }

    public int mul(int a, int b)
    {
        FPNumber fa = new FPNumber(a);
        FPNumber fb = new FPNumber(b);
        FPNumber result = new FPNumber(0);
        
/***********************************************
  1. Handle exception values
***********************************************/
        // if either input NaN, return NaN input
        if(fa.isNaN() || fb.isNaN())
        {
            if(fa.isNaN())
            {
                result.setS(fa.s());
                result.setE(fa.e());
                result.setF(fa.f());
            }
            else if(fb.isNaN())
            {
                result.setS(fb.s());
                result.setE(fb.e());
                result.setF(fb.f());
            }
            return result.asInt();
        }
        // if one input 0 and other input infinity, return NaN
        else if((fa.isZero() && fb.isInfinity()) || (fb.isZero() && fa.isInfinity()))
        {
            result.setS(1);
            result.setE(255);
            result.setF(12);
            return result.asInt();
        }
        // if either input 0, return 0. result sign is XOR of input signs
        else if(fa.isZero() || fb.isZero())
        {
            if(fa.s() == fb.s())
            {
                result.setS(1);
            }
            else
            {
                result.setS(-1);
            }
            result.setE(0);
            result.setF(0);
            return result.asInt();
        }
        // if either input infinity, return infinity. result sign is XOR of input signs
        else if(fa.isInfinity() || fb.isInfinity())
        {
            if(fa.s() == fb.s())
            {
                result.setS(1);
            }
            else
            {
                result.setS(-1);
            }
            
            result.setE(255);
            result.setF(0); 
            return result.asInt();
        }
         
        // XOR input signs
        if(fa.s() == fb.s())
        {
            result.setS(1);
        }
        else
        {
            result.setS(-1);
        }
/***********************************************
  2. Add exponents
***********************************************/
        int exponent = 0;
        // if exponent > 254, overflow and return infinity
        if((fa.e() + fb.e() - 127) > 254)
        {
            result.setE(255);
            result.setF(0); 
        }
        // if exponent < 0, underflow and return 0
        else if((fa.e() + fb.e() - 127) < 0)
        {
            result.setE(0);
            result.setF(0);
        }
        else
        {
            exponent = fa.e() + fb.e() - 127;
        }
/***********************************************
  3. Multiply the significands
***********************************************/
        long t = (fa.f() * fb.f()) >> 25;
/***********************************************
  4. Normalize and round
***********************************************/
        if (((t >> 26) & 1) == 1)
        {
            if(fa.e() + 1 == 255)
            {
                result.setS(fa.s());
                result.setE(255);
                result.setF(0);
            }
            else
            {
                result.setE(exponent + 1);
                t = t >> 1;
            }
        }
        else
        {
            result.setE(exponent);
        }
        result.setF(t);
        
        return result.asInt();
    }

    public static void main(String[] args)
    {
        int v24_25	= 0x41C20000; // 24.25
        int v_1875	= 0xBE400000; // -0.1875
        int v5		= 0xC0A00000; // -5.0
        // in-class example
        int v47_625     = 0x423E8000; // 47.625
        int v13_75      = 0x415c0000; // 13.75
        // tests
        int v15         = 0x41700000; // 15.0
        int v22         = 0x41B00000; // 22.0
        int v4_8        = 0xc099999A; // -4.8
        int v200        = 0xC3480000; // -200.0
        
        fp m = new fp();
        
        System.out.printf("%7s +  %-6s =  %s\t   ans:  24.25\n", Float.intBitsToFloat(v24_25), Float.intBitsToFloat(0), Float.intBitsToFloat(m.add(v24_25, 0)));
        System.out.printf("%7s + %-7s =  %s\t   ans:  24.0625\n", Float.intBitsToFloat(v24_25), Float.intBitsToFloat(v_1875), Float.intBitsToFloat(m.add(v24_25, v_1875)));
        System.out.printf("%7s + %-7s =  %s \t   ans:  19.25\n", Float.intBitsToFloat(v24_25), Float.intBitsToFloat(v5), Float.intBitsToFloat(m.add(v24_25, v5)));
        System.out.printf("%7s + %-7s = %s \t   ans: -5.1875\n", Float.intBitsToFloat(v_1875), Float.intBitsToFloat(v5), Float.intBitsToFloat(m.add(v_1875, v5)));
        System.out.printf("%7s +  %-7s=  %s\t   ans:  61.375\n", Float.intBitsToFloat(v13_75), Float.intBitsToFloat(v47_625), Float.intBitsToFloat(m.add(v13_75, v47_625)));
        System.out.printf("%7s +  %-7s=  %s\t   ans:  37.0\n", Float.intBitsToFloat(v15), Float.intBitsToFloat(v22), Float.intBitsToFloat(m.add(v15, v22)));
        System.out.printf("%7s + %-7s = %s\t   ans: -204.8\n", Float.intBitsToFloat(v4_8), Float.intBitsToFloat(v200), Float.intBitsToFloat(m.add(v4_8, v200)));
        System.out.printf("%7s +  %-6s = %s\t   ans: -200.0\n", Float.intBitsToFloat(v200), Float.intBitsToFloat(0), Float.intBitsToFloat(m.add(v200, 0)));
        System.out.println();
        System.out.printf("%7s *  %-6s =  %s\t   ans:  0.0\n", Float.intBitsToFloat(v24_25), Float.intBitsToFloat(0), Float.intBitsToFloat(m.mul(v24_25, 0)));
        System.out.printf("%7s * %-7s = %s\t   ans: -4.546875\n", Float.intBitsToFloat(v24_25), Float.intBitsToFloat(v_1875), Float.intBitsToFloat(m.mul(v24_25, v_1875)));
        System.out.printf("%7s * %-7s = %s\t   ans: -121.25\n", Float.intBitsToFloat(v24_25), Float.intBitsToFloat(v5), Float.intBitsToFloat(m.mul(v24_25, v5)));
        System.out.printf("%7s * %-7s =  %s\t   ans:  0.9375\n", Float.intBitsToFloat(v_1875), Float.intBitsToFloat(v5), Float.intBitsToFloat(m.mul(v_1875, v5)));
        System.out.printf("%7s *  %-7s=  %s\t   ans:  654.84375\n", Float.intBitsToFloat(v13_75), Float.intBitsToFloat(v47_625), Float.intBitsToFloat(m.mul(v13_75, v47_625)));
        System.out.printf("%7s *  %-7s=  %s\t   ans:  330.0\n", Float.intBitsToFloat(v15), Float.intBitsToFloat(v22), Float.intBitsToFloat(m.mul(v15, v22)));
        System.out.printf("%7s * %-7s =  %s\t   ans:  960.0\n", Float.intBitsToFloat(v4_8), Float.intBitsToFloat(v200), Float.intBitsToFloat(m.mul(v4_8, v200)));
        System.out.printf("%7s *  %-6s = %s\t   ans:  0.0\n", Float.intBitsToFloat(v200), Float.intBitsToFloat(0), Float.intBitsToFloat(m.mul(v200, 0)));
    }
}

