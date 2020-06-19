package com.android.demo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.demo.search.di.useCasesModule
import com.android.demo.search.utils.Data
import com.android.demo.search.utils.Status
import com.android.demo.search.viewmodel.ArtistViewModel
import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.android.domain.entities.GetArtistUseCase
import com.android.domain.utils.Result
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.*
import org.mockito.Mockito.`when` as whenever

private const val VALID_ID = "The Week"
private const val INVALID_ID = ""

class ArtistViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var subject: ArtistViewModel

    @Mock lateinit var artistListValidResult: Result.Success<List<Artist>>
    @Mock lateinit var artistAlbumListValidResult: Result.Success<List<Album>>
    @Mock lateinit var artistListInvalidResult: Result.Failure
    @Mock lateinit var albumListInvalidResult: Result.Failure
    @Mock lateinit var artistList: List<Artist>
    @Mock lateinit var albumList: List<Album>
    @Mock lateinit var exception: Exception

    private val getCharacterByIdUseCase: GetArtistUseCase by inject()

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            modules(listOf(useCasesModule))
        }

        declareMock<GetArtistUseCase>()
        MockitoAnnotations.initMocks(this)
        subject = ArtistViewModel(getCharacterByIdUseCase)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun onSearchArtistSuccessful() {
        val liveDataUnderTest = subject.getArtistSearchList().testObserver()
        whenever(getCharacterByIdUseCase.searchArtist(VALID_ID)).thenReturn(artistListValidResult)
        whenever(artistListValidResult.data).thenReturn(artistList)
        runBlocking {
            subject.onSearchArtist(VALID_ID).join()
        }
        Truth.assertThat(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(Data(Status.LOADING), Data(Status.SUCCESSFUL, data = artistList)))

    }

    @Test
    fun onTopArtistSuccessful() {
        val liveDataUnderTest = subject.getTopArtisListLiveData().testObserver()
        whenever(getCharacterByIdUseCase.getTopArtist()).thenReturn(artistListValidResult)
        whenever(artistListValidResult.data).thenReturn(artistList)
        runBlocking {
            subject.getTopArtistList().join()
        }
        Truth.assertThat(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(Data(Status.LOADING), Data(Status.SUCCESSFUL, data = artistList)))

    }

    @Test
    fun onArtistTopAlbumListSuccessful() {
        val liveDataUnderTest = subject.getAlbumListLiveData().testObserver()
        whenever(getCharacterByIdUseCase.getArtistAlbums(VALID_ID)).thenReturn(artistAlbumListValidResult)
        whenever(artistAlbumListValidResult.data).thenReturn(albumList)
        runBlocking {
            subject.getArtistAlbumList(VALID_ID).join()
        }
        Truth.assertThat(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(Data(Status.LOADING), Data(Status.SUCCESSFUL, data = albumList)))

    }

    @Test
    fun onArtistTopAlbumListError() {
        val liveDataUnderTest = subject.getAlbumListLiveData().testObserver()
        whenever(getCharacterByIdUseCase.getArtistAlbums(INVALID_ID)).thenReturn(albumListInvalidResult)
        whenever(albumListInvalidResult.exception).thenReturn(exception)
        runBlocking {
            subject.getArtistAlbumList(INVALID_ID).join()
        }
        Truth.assertThat(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(Data(Status.LOADING), Data(Status.ERROR, data = null, error = exception)))

    }



    @Test
    fun onSearchArtistTestError() {
        val liveDataUnderTest = subject.getArtistSearchList().testObserver()
        whenever(getCharacterByIdUseCase.searchArtist(INVALID_ID)).thenReturn(artistListInvalidResult)
        whenever(artistListInvalidResult.exception).thenReturn(exception)

        runBlocking {
            subject.onSearchArtist(INVALID_ID).join()
        }

        Truth.assertThat(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(Data(Status.LOADING), Data(Status.ERROR, data = null, error = exception)))
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }

}
