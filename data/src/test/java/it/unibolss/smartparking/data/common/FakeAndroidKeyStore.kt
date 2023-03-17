package it.unibolss.smartparking.data.common

import java.io.InputStream
import java.io.OutputStream
import java.security.Key
import java.security.KeyStore
import java.security.KeyStoreSpi
import java.security.Provider
import java.security.SecureRandom
import java.security.Security
import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.util.Collections
import java.util.Date
import java.util.Enumeration
import javax.crypto.KeyGenerator
import javax.crypto.KeyGeneratorSpi
import javax.crypto.SecretKey

object FakeAndroidKeyStore {

    val setup by lazy {
        Security.addProvider(object : Provider("AndroidKeyStore", 1.0, "") {
            init {
                put("KeyStore.AndroidKeyStore", FakeKeyStore::class.java.name)
                put("KeyGenerator.AES", FakeAesKeyGenerator::class.java.name)
            }
        })
    }
    @Suppress("TooManyFunctions")
    class FakeKeyStore : KeyStoreSpi() {
        override fun engineIsKeyEntry(alias: String?): Boolean = wrapped.isKeyEntry(alias)

        override fun engineIsCertificateEntry(alias: String?): Boolean = wrapped.isCertificateEntry(alias)

        override fun engineGetCertificate(alias: String?): Certificate = wrapped.getCertificate(alias)

        override fun engineGetCreationDate(alias: String?): Date = wrapped.getCreationDate(alias)

        override fun engineDeleteEntry(alias: String?) {
            storedKeys
            storedKeys.remove(alias)
        }

        override fun engineSetKeyEntry(alias: String?, key: Key?, password: CharArray?, chain: Array<out Certificate>?) =
            wrapped.setKeyEntry(alias, key, password, chain)

        override fun engineSetKeyEntry(alias: String?, key: ByteArray?, chain: Array<out Certificate>?) = wrapped.setKeyEntry(alias, key, chain)

        override fun engineStore(stream: OutputStream?, password: CharArray?) = wrapped.store(stream, password)

        override fun engineSize(): Int = wrapped.size()

        override fun engineAliases(): Enumeration<String> = Collections.enumeration(storedKeys.keys)

        override fun engineContainsAlias(alias: String?): Boolean = storedKeys.containsKey(alias)

        override fun engineLoad(stream: InputStream?, password: CharArray?) = wrapped.load(stream, password)

        override fun engineGetCertificateChain(alias: String?): Array<Certificate>? = wrapped.getCertificateChain(alias)

        override fun engineSetCertificateEntry(alias: String?, cert: Certificate?) = wrapped.setCertificateEntry(alias, cert)

        override fun engineGetCertificateAlias(cert: Certificate?): String? = wrapped.getCertificateAlias(cert)

        override fun engineGetKey(alias: String?, password: CharArray?): Key? = (storedKeys[alias] as? KeyStore.SecretKeyEntry)?.secretKey

        override fun engineGetEntry(p0: String, p1: KeyStore.ProtectionParameter?): KeyStore.Entry? = storedKeys[p0]

        override fun engineSetEntry(p0: String, p1: KeyStore.Entry, p2: KeyStore.ProtectionParameter?) {
            storedKeys[p0] = p1
        }

        override fun engineLoad(p0: KeyStore.LoadStoreParameter?) = wrapped.load(p0)

        override fun engineStore(p0: KeyStore.LoadStoreParameter?) = wrapped.store(p0)

        override fun engineEntryInstanceOf(p0: String?, p1: Class<out KeyStore.Entry>?) = wrapped.entryInstanceOf(p0, p1)

        companion object {
            private val wrapped = KeyStore.getInstance("BKS", "BC")
            internal val storedKeys = mutableMapOf<String, KeyStore.Entry>()
        }
    }

    @Suppress("unused")
    class FakeAesKeyGenerator : KeyGeneratorSpi() {
        private val wrapped = KeyGenerator.getInstance("AES")

        override fun engineInit(random: SecureRandom?) = Unit
        override fun engineInit(params: AlgorithmParameterSpec?, random: SecureRandom?) = Unit
        override fun engineInit(keysize: Int, random: SecureRandom?) = Unit
        override fun engineGenerateKey(): SecretKey = wrapped.generateKey()
    }
}
