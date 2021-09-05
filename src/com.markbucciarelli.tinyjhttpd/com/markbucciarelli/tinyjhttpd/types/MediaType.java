package com.markbucciarelli.tinyjhttpd.types;

/**
 A list if common HTTP media types, taken from
 https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types.

 <p>
 More commonly referred to as MIME (Multipurpose Internet Mail Extensions),
 the latest Internet Assigned Numbers Authority (IANA) document renames them as
 Media Types; see https://www.iana.org/assignments/media-types/media-types.xhtml.
 */
public enum MediaType {
  AAC("audio/aac", ".aac", "AAC audio"),
  ABW("application/x-abiword", ".abw", "AbiWord document"),
  ARC(
    "application/x-freearc",
    ".arc",
    "Archive document (multiple files embedded)"
  ),
  AVI("video/x-msvideo", ".avi", "AVI: Audio Video Interleave"),
  AZW("application/vnd.amazon.ebook", ".azw", "Amazon Kindle eBook format"),
  BIN("application/octet-stream", ".bin", "Any kind of binary data"),
  BMP("image/bmp", ".bmp", "Windows OS/2 Bitmap Graphics"),
  BZ("application/x-bzip", ".bz", "BZip archive"),
  BZ2("application/x-bzip2", ".bz2", "BZip2 archive"),
  CDA("application/x-cdf", ".cda", "CD audio"),
  CSH("application/x-csh", ".csh", "C-Shell script"),
  CSS("text/css", ".css", "Cascading Style Sheets (CSS)"),
  CSV("text/csv", ".csv", "Comma-separated values (CSV)"),
  DOC("application/msword", ".doc", "Microsoft Word"),
  DOCX(
    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    ".docx",
    "Microsoft Word (OpenXML)"
  ),
  EOT("application/vnd.ms-fontobject", ".eot", "MS Embedded OpenType fonts"),
  EPUB("application/epub+zip", ".epub", "Electronic publication (EPUB)"),
  GZ("application/gzip", ".gz", "GZip Compressed Archive"),
  GIF("image/gif", ".gif", "Graphics Interchange Format (GIF)"),
  HTM("text/html", ".htm", "HyperText Markup Language (HTML)"),
  HTML("text/html", ".html", "HyperText Markup Language (HTML)"),
  ICO("image/vnd.microsoft.icon", ".ico", "Icon format"),
  ICS("text/calendar", ".ics", "iCalendar format"),
  JAR("application/java-archive", ".jar", "Java Archive (JAR)"),
  JPEG("image/jpeg", ".jpeg", "JPEG images"),
  JPG("image/jpeg", ".jpg", "JPEG images"),
  JS(
    "text/javascript (Specifications: HTML and its reasoning, and IETF)",
    ".js",
    "JavaScript"
  ),
  JSON("application/json", ".json", "JSON format"),
  JSONLD("application/ld+json", ".jsonld", "JSON-LD format"),
  MID(
    "audio/midi audio/x-midi",
    ".mid",
    "Musical Instrument Digital Interface (MIDI)"
  ),
  MIDI(
    "audio/midi audio/x-midi",
    ".midi",
    "Musical Instrument Digital Interface (MIDI)"
  ),
  MJS("text/javascript", ".mjs", "JavaScript module"),
  MP3("audio/mpeg", ".mp3", "MP3 audio"),
  MP4("video/mp4", ".mp4", "MP4 video"),
  MPEG("video/mpeg", ".mpeg", "MPEG Video"),
  MPKG(
    "application/vnd.apple.installer+xml",
    ".mpkg",
    "Apple Installer Package"
  ),
  ODP(
    "application/vnd.oasis.opendocument.presentation",
    ".odp",
    "OpenDocument presentation document"
  ),
  ODS(
    "application/vnd.oasis.opendocument.spreadsheet",
    ".ods",
    "OpenDocument spreadsheet document"
  ),
  ODT(
    "application/vnd.oasis.opendocument.text",
    ".odt",
    "OpenDocument text document"
  ),
  OGA("audio/ogg", ".oga", "OGG audio"),
  OGV("video/ogg", ".ogv", "OGG video"),
  OGX("application/ogg", ".ogx", "OGG"),
  OPUS("audio/opus", ".opus", "Opus audio"),
  OTF("font/otf", ".otf", "OpenType font"),
  PNG("image/png", ".png", "Portable Network Graphics"),
  PDF("application/pdf", ".pdf", "Adobe Portable Document Format (PDF)"),
  PHP(
    "application/x-httpd-php",
    ".php",
    "Hypertext Preprocessor (Personal Home Page)"
  ),
  PPT("application/vnd.ms-powerpoint", ".ppt", "Microsoft PowerPoint"),
  PPTX(
    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
    ".pptx",
    "Microsoft PowerPoint (OpenXML)"
  ),
  RAR("application/vnd.rar", ".rar", "RAR archive"),
  RTF("application/rtf", ".rtf", "Rich Text Format (RTF)"),
  SH("application/x-sh", ".sh", "Bourne shell script"),
  SVG("image/svg+xml", ".svg", "Scalable Vector Graphics (SVG)"),
  SWF(
    "application/x-shockwave-flash",
    ".swf",
    "Small web format (SWF) or Adobe Flash document"
  ),
  TAR("application/x-tar", ".tar", "Tape Archive (TAR)"),
  TIF("image/tiff", ".tif", "Tagged Image File Format (TIFF)"),
  TIFF("image/tiff", ".tiff", "Tagged Image File Format (TIFF)"),
  TS("video/mp2t", ".ts", "MPEG transport stream"),
  TTF("font/ttf", ".ttf", "TrueType Font"),
  TXT("text/plain", ".txt", "Text, (generally ASCII or ISO 8859-n)"),
  VSD("application/vnd.visio", ".vsd", "Microsoft Visio"),
  WAV("audio/wav", ".wav", "Waveform Audio Format"),
  WEBA("audio/webm", ".weba", "WEBM audio"),
  WEBM("video/webm", ".webm", "WEBM video"),
  WEBP("image/webp", ".webp", "WEBP image"),
  WOFF("font/woff", ".woff", "Web Open Font Format (WOFF)"),
  WOFF2("font/woff2", ".woff2", "Web Open Font Format (WOFF)"),
  XHTML("application/xhtml+xml", ".xhtml", "XHTML"),
  XLS("application/vnd.ms-excel", ".xls", "Microsoft Excel"),
  XLSX(
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    ".xlsx",
    "Microsoft Excel (OpenXML)"
  ),
  XML_UNREADABLE(
    "application/xml",
    ".xml",
    "if not readable from casual users (RFC 3023, section 3)"
  ),
  XML(
    "text/xml",
    ".xml",
    "if readable from casual users (RFC 3023, section 3)"
  ),
  XUL("application/vnd.mozilla.xul+xml", ".xul", "XUL"),
  ZIP("application/zip", ".zip", "ZIP archive"),
  THREE_GP_VIDEO("video/3gpp", ".3gp", "3GPP audio/video container"),
  THREE_GP_NO_VIDEO("audio/3gpp", ".3gp", "3GPP audio/video container"),
  THREE_G2_VIDEO("video/3gpp2", ".3g2", "3GPP2 audio/video container"),
  THREE_G2_NO_VIDEO("audio/3gpp2", ".3g2", "3GPP2 audio/video container"),
  SEVEN_Z("application/x-7z-compressed", ".7z", "7-zip archive");

  private final String value;
  private final String fileExtension;
  private final String description;

  MediaType(String value, String fileExtension, String description) {
    this.value = value;
    this.fileExtension = fileExtension;
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public String getDescription() {
    return description;
  }
}
