package java.util

import scala.scalajs.locale.LocaleRegistry
import scala.collection.{Map => SMap}

object Locale {

  private var defaultLocale: Option[Locale] = None
  private var defaultPerCategory: SMap[Category, Option[Locale]] =
    Category.values().map(_ -> None).toMap

  val ENGLISH: Locale             = LocaleRegistry.en.toLocale
  val FRENCH: Locale              = LocaleRegistry.fr.toLocale
  val GERMAN: Locale              = LocaleRegistry.de.toLocale
  val ITALIAN: Locale             = LocaleRegistry.it.toLocale
  val JAPANESE: Locale            = LocaleRegistry.ja.toLocale
  val KOREAN: Locale              = LocaleRegistry.ko.toLocale
  val CHINESE: Locale             = LocaleRegistry.zh.toLocale
  val SIMPLIFIED_CHINESE: Locale  = LocaleRegistry.zh_CN_Hans.toLocale
  val TRADITIONAL_CHINESE: Locale = LocaleRegistry.zh_TW_Hant.toLocale
  val FRANCE: Locale              = LocaleRegistry.fr_FR.toLocale
  val GERMANY: Locale             = LocaleRegistry.de_DE.toLocale
  val ITALY: Locale               = LocaleRegistry.it_IT.toLocale
  val JAPAN: Locale               = LocaleRegistry.ja_JP.toLocale
  val KOREA: Locale               = LocaleRegistry.ko_KR.toLocale
  val CHINA: Locale               = LocaleRegistry.zh_CN_Hans.toLocale
  val PRC: Locale                 = LocaleRegistry.zh_CN_Hans.toLocale
  val TAIWAN: Locale              = LocaleRegistry.zh_TW_Hant.toLocale
  val UK: Locale                  = LocaleRegistry.en_GB.toLocale
  val US: Locale                  = LocaleRegistry.en_US.toLocale
  val CANADA: Locale              = LocaleRegistry.en_CA.toLocale
  val CANADA_FRENCH: Locale       = LocaleRegistry.fr_CA.toLocale
  val ROOT: Locale                = new Locale("", "", "")

  val PRIVATE_USE_EXTENSION: Char = 'x'
  val UNICODE_LOCALE_EXTENSION: Char = 'u'

  final class Category private (name: String, ordinal: Int)
    extends Enum[Category](name, ordinal)

  object Category {
    val DISPLAY = new Category("DISPLAY", 0)
    val FORMAT  = new Category("FORMAT", 1)

    private val categories = Array(DISPLAY, FORMAT)

    def values(): Array[Category] = categories

    def valueOf(name: String): Category = categories.find(_.name == name).getOrElse {
      throw if (name == null) new NullPointerException("Argument cannot be null")
            else new IllegalArgumentException(s"No such category: $name")
    }
  }

  def getAvailableLocales(): Array[Locale] = Array(US, CANADA)

  def getDefault(): Locale = defaultLocale
    .getOrElse(throw new IllegalStateException("No default locale set"))

  def getDefault(category: Category): Locale = {
    if (category == null) throw new NullPointerException("Argument cannot be null")
    else defaultPerCategory.get(category).flatten
      .getOrElse(throw new IllegalStateException(s"No default locale set for category $category"))
  }

  def setDefault(newLocale: Locale): Unit = {
    defaultLocale = Some(newLocale)
    defaultPerCategory = Category.values().map(_ -> Some(newLocale)).toMap
  }

  def setDefault(category: Category, newLocale: Locale): Unit = {
    if (category == null || newLocale == null) throw new NullPointerException("Argument cannot be null")
    else defaultPerCategory = defaultPerCategory + (category -> Some(newLocale))
  }

  def forLanguageTag(languageTag: String): Locale = LocaleRegistry
    .localeForLanguageTag(languageTag).getOrElse(ROOT)
}

class Locale(private val language: String,
             private val country: String,
             private val variant: String) {

  // Required by the javadocs
  if (language == null || country == null || variant == null) {
    throw new NullPointerException("Null argument to constructor not allowed")
  }

  // Additional constructors
  def this(language: String, country: String) = this(language, country, "")
  def this(language: String) = this(language, "", "")

  def getLanguage(): String = language
  def getCountry(): String = country
  def getVariant(): String = variant
  def getScript(): String = ""

  // TODO Add other methods on the public interface
  def getUnicodeLocaleType(key: String):String = ???

  override def equals(x: Any):Boolean = x match {
    case l: Locale =>
      language == l.language && country == l.country && variant == l.variant
    case _         =>
      false
  }
}
