import org.junit.Assert

import org.junit.Test

class MainTest {
    @Test
    fun `Test creation`() {
        val jsonObj = buildJson {
            "glossary"{
                "title"("example glossary")
                "GlossDiv" {
                    "title"("S")
                    "GlossList" {
                        "GlossEntry" {
                            "ID"("SGML")
                            "SortAs"("SGML")
                            "GlossTerm"("Standard Generalized Markup Language")
                            "Acronym"("SGML")
                            "Abbrev"("ISO 8879:1986")
                            "GlossDef" {
                                "para"("A meta-markup language, used to create markup languages such as DocBook.")
                                "GlossSeeAlso"["GML", "XML"]
                            }
                            "GlossSee"("markup")
                        }
                    }
                }
                "woosh"[1, 2]
            }
        }

        Assert.assertEquals(jsonObj.toString(), """{"glossary":{"title":"example glossary","woosh":[1,2],"GlossDiv":{"GlossList":{"GlossEntry":{"GlossTerm":"Standard Generalized Markup Language","GlossSee":"markup","SortAs":"SGML","GlossDef":{"para":"A meta-markup language, used to create markup languages such as DocBook.","GlossSeeAlso":["GML","XML"]},"ID":"SGML","Acronym":"SGML","Abbrev":"ISO 8879:1986"}},"title":"S"}}}""")
    }
}