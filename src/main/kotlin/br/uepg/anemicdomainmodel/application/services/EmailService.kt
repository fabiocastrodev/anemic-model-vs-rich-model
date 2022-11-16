package br.uepg.anemicdomainmodel.application.services

interface EmailService {
    fun enviarCodigoRastreio(email: String, codigoRastreio: String)
}