package br.com.brunoccbertolini.easyandfastnotes_noads.data

import java.util.*

class SampleDataProvider {

    companion object {
        private val sampleText1 = "A Easy and Fast Note Sample"
        private val sampleText2 = "Sample Note \n Easy and Fast!"
        private val sampleText3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ultricies dolor urna, eu imperdiet mi commodo a. Praesent gravida dolor ligula, eu sodales tellus ornare id. Sed fermentum odio nec interdum pretium. Morbi ut pharetra elit. Nulla sed felis nec dolor tincidunt interdum. Pellentesque efficitur purus nisi, ac molestie nulla consectetur sagittis. Integer vulputate non risus nec imperdiet. Praesent eu nisl accumsan, lacinia mi in, tempus neque. Vestibulum sed auctor risus. Etiam non mi tincidunt, lacinia quam pulvinar, cursus ex. Donec vel dolor vel turpis cursus porttitor. Praesent quis eleifend ligula.

            Cras posuere ligula quis fermentum pharetra. Suspendisse ac imperdiet libero. Sed sit amet sem sed tellus imperdiet dictum. Nulla facilisi. Etiam metus massa, fermentum in pretium ac, gravida nec quam. Curabitur nulla massa, commodo vitae tempus ac, ornare vel mauris. Curabitur quis tincidunt felis, eu rutrum risus. Ut molestie non metus congue congue. Nunc in ligula a ante commodo ornare. Nulla molestie est sit amet est pretium consectetur.
            """.trimIndent()


        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        fun getNotes() = arrayListOf(
            NoteEntity(1, getDate(0), sampleText1),
            NoteEntity(2, getDate(1), sampleText2),
            NoteEntity(3, getDate(2), sampleText3),

            )
    }
}