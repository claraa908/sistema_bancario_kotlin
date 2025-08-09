import kotlin.random.Random

class Banco(titular: String, saldo: Double) {
    var contas: HashMap<Int, ContaInfo> = HashMap()
    var numero: Int = 0
    var limiteOperacoes: Int = 10
    var extrato = Array(10) {0.0}

    init {
        var nConta : Int
        do{
            nConta = Random.nextInt(1000, 9999)
        }while(contas.containsKey(nConta))
        contas[nConta] = ContaInfo(titular, saldo)
        numero = nConta
        println("Bem-vindo senhor(a), $titular \n" +
                "O seu saldo inicial eh R$" + getSaldo() + "\n" +
                "O número de acesso a sua conta eh $nConta \n")
    }

    fun getSaldo() : Double {
        return contas[getnConta()]!!.saldo + contas[getnConta()]!!.limite
    }

    fun getnConta() : Int{
        return numero
    }

    fun getLimite() :  Double{
        return contas[getnConta()]!!.limite
    }

    fun saque(valor: Double): Boolean{
        if(valor <= 0){
            println("Nao eh possivel transferir valor negativo ou nulo")
            return false
        }

        if(valor > getSaldo()){
            println("Saldo insuficiente")
            return false
        }

        if(limiteOperacoes > 0) {
            if(valor < 0){
                println("Nao eh possivel sacar valor negativo")
                return false
            }
            if(valor > contas[getnConta()]!!.saldo){
                contas[getnConta()]!!.limite -= (valor - contas[getnConta()]!!.saldo)
                contas[getnConta()]!!.saldo = 0.0
            }else{
                contas[getnConta()]!!.saldo -= valor
            }
            limiteOperacoes--
            for(i in extrato.indices){
                if(extrato[i] == 0.0){
                    extrato[i] = -valor
                    break
                }
            }
            return true
        }
        return false
    }

    fun transferir(destinatario: Int, valor: Double): Boolean {
        if(valor <= 0){
            println("Nao eh possivel transferir valor negativo ou nulo")
            return false
        }

        if(valor > getSaldo()){
            println("Saldo insuficiente")
            return false
        }

        if(contas.containsKey(destinatario) && limiteOperacoes > 0) {
            if(valor > contas[getnConta()]!!.saldo){
                contas[destinatario]!!.saldo += valor
                contas[getnConta()]!!.limite -= (valor - contas[getnConta()]!!.saldo)
                contas[getnConta()]!!.saldo = 0.0
            }else{
                contas[destinatario]!!.saldo += valor
                contas[getnConta()]!!.saldo -= valor
            }
            limiteOperacoes--
            for(i in extrato.indices){
                if(extrato[i] == 0.0){
                    extrato[i] = -valor
                    break
                }
            }
            return true
        }
        return false
    }

    fun depositar(valor: Double): Boolean {
        if(limiteOperacoes > 0){
            if(valor <= 0){
                println("Nao eh possivel depositar valor negativo ou nulo")
                return false
            }
            if(contas[getnConta()]!!.limite < 100){
                var diferenca = 100 - contas[getnConta()]!!.limite
                if(diferenca <= 0){
                    contas[getnConta()]!!.limite += valor
                }else{
                    contas[getnConta()]!!.limite = 100.00
                    contas[getnConta()]!!.saldo = valor - diferenca
                }
            }else{
                contas[getnConta()]!!.saldo += valor
            }
            limiteOperacoes--
            for(i in extrato.indices){
                if(extrato[i] == 0.0){
                    extrato[i] = valor
                    break
                }
            }
            return true
        }
        return false
    }
}