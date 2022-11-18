package assessment.narayanagroup.githubapisearch.data

import kotlinx.coroutines.Dispatchers

object Constant {

    const val IN_QUALIFIER = "in:name,description"
    const val NETWORK_PAGE_SIZE = 10
    const val GITHUB_STARTING_PAGE_INDEX = 1

    val DISPATCHER_IO = Dispatchers.IO
    val DISPATCHER_DEFAULT = Dispatchers.Default
    val DISPATCHER_MAIN = Dispatchers.Main
}