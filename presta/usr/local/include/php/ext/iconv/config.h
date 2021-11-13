/* config.h.  Generated from config.h.in by configure.  */
/* config.h.in.  Generated from configure.ac by autoheader.  */

/* Whether to build iconv as dynamic module */
#define COMPILE_DL_ICONV 1

/* Konstantin Chuguev's iconv implementation */
/* #undef HAVE_BSD_ICONV */

/* Define to 1 if you have the <dlfcn.h> header file. */
#define HAVE_DLFCN_H 1

/* */
/* #undef HAVE_GICONV_H */

/* glibc's iconv implementation */
#define HAVE_GLIBC_ICONV 1

/* IBM iconv implementation */
/* #undef HAVE_IBM_ICONV */

/* */
#define HAVE_ICONV 1

/* Define to 1 if you have the <inttypes.h> header file. */
#define HAVE_INTTYPES_H 1

/* */
/* #undef HAVE_LIBICONV */

/* Define to 1 if you have the <memory.h> header file. */
#define HAVE_MEMORY_H 1

/* Define to 1 if you have the <stdint.h> header file. */
#define HAVE_STDINT_H 1

/* Define to 1 if you have the <stdlib.h> header file. */
#define HAVE_STDLIB_H 1

/* Define to 1 if you have the <strings.h> header file. */
#define HAVE_STRINGS_H 1

/* Define to 1 if you have the <string.h> header file. */
#define HAVE_STRING_H 1

/* Define to 1 if you have the <sys/stat.h> header file. */
#define HAVE_SYS_STAT_H 1

/* Define to 1 if you have the <sys/types.h> header file. */
#define HAVE_SYS_TYPES_H 1

/* Define to 1 if you have the <unistd.h> header file. */
#define HAVE_UNISTD_H 1

/* iconv() is aliased to libiconv() in -liconv */
/* #undef ICONV_ALIASED_LIBICONV */

/* Whether iconv supports IGNORE */
#define ICONV_BROKEN_IGNORE 1

/* Whether iconv supports error no or not */
#define ICONV_SUPPORTS_ERRNO 1

/* Define to the address where bug reports for this package should be sent. */
/* #undef PACKAGE_BUGREPORT */

/* Define to the full name of this package. */
/* #undef PACKAGE_NAME */

/* Define to the full name and version of this package. */
/* #undef PACKAGE_STRING */

/* Define to the one symbol short name of this package. */
/* #undef PACKAGE_TARNAME */

/* Define to the home page for this package. */
/* #undef PACKAGE_URL */

/* Define to the version of this package. */
/* #undef PACKAGE_VERSION */

/* Path to iconv.h */
#define PHP_ICONV_H_PATH </usr/include/iconv.h>

/* Which iconv implementation to use */
#define PHP_ICONV_IMPL "glibc"

/* Define to 1 if you have the ANSI C header files. */
#define STDC_HEADERS 1
