package com.example.networkguardian

object ConnectionRuleManager {
    private var blockAllMode = false

    private var allowedIPs = mutableSetOf<String>()
    private var allowedDomains = mutableSetOf<String>()
    private var allowedPorts = mutableSetOf<Int>()
    private var allowedProtocols = mutableSetOf<String>()

    fun isAllowed(ip: String?, domain: String?, port: Int?, protocol: String?): Boolean {
        if (!blockAllMode) return true

        val ipOk = ip?.let { allowedIPs.any { ip.startsWith(it) } } ?: false
        val domainOk = domain?.let { allowedDomains.any { domain.contains(it) } } ?: false
        val portOk = port?.let { allowedPorts.contains(it) } ?: false
        val protocolOk = protocol?.let { allowedProtocols.contains(it.uppercase()) } ?: false

        return ipOk || domainOk || portOk || protocolOk
    }

    fun toggleBlockAll(enable: Boolean) {
        blockAllMode = enable
    }

    fun addIP(ip: String) = allowedIPs.add(ip)
    fun addDomain(domain: String) = allowedDomains.add(domain)
    fun addPort(port: Int) = allowedPorts.add(port)
    fun addProtocol(protocol: String) = allowedProtocols.add(protocol.uppercase())

    fun removeIP(ip: String) = allowedIPs.remove(ip)
    fun removeDomain(domain: String) = allowedDomains.remove(domain)
    fun removePort(port: Int) = allowedPorts.remove(port)
    fun removeProtocol(protocol: String) = allowedProtocols.remove(protocol.uppercase())

    fun getAllowedIPs(): List<String> = allowedIPs.toList()
    fun getAllowedDomains(): List<String> = allowedDomains.toList()
    fun getAllowedPorts(): List<Int> = allowedPorts.toList()
    fun getAllowedProtocols(): List<String> = allowedProtocols.toList()

    fun loadAll(context: Context) {
        allowedIPs = RuleStorage.loadList(context, "allowed_ips")
        allowedDomains = RuleStorage.loadList(context, "allowed_domains")
        allowedProtocols = RuleStorage.loadList(context, "allowed_protocols")
        allowedPorts = RuleStorage.loadList(context, "allowed_ports").mapNotNull { it.toIntOrNull() }.toMutableSet()
    }

    fun saveAll(context: Context) {
        RuleStorage.saveList(context, "allowed_ips", allowedIPs)
        RuleStorage.saveList(context, "allowed_domains", allowedDomains)
        RuleStorage.saveList(context, "allowed_protocols", allowedProtocols)
        RuleStorage.saveList(context, "allowed_ports", allowedPorts.map { it.toString() }.toSet())
    }
}
