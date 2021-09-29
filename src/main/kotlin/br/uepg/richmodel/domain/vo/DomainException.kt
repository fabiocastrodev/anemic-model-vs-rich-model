package br.uepg.richmodel.domain.vo

abstract class DomainException(val code: String, message: String) : Exception(message)