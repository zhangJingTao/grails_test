/**
 * 存储push信息
 */
class GitPush {

    static constraints = {
        repositoryId(nullable: true)
        repositoryName(nullable: true)
        repositoryUrl(nullable: true)
        repositoryDesc(nullable: true)
        repositoryHomePage(nullable: true)
        commitId(nullable: true)
        commitDate(nullable: true)
        commitUrl(nullable: true)
        commitAuthorName(nullable: true)
        commitAuthorEmail(nullable: true)
        commitMsg(nullable: true)
    }
    /**
     * 项目信息
     */
    String repositoryId
    String repositoryName
    String repositoryUrl
    String repositoryDesc
    String repositoryHomePage
    /**
     * Commits信息
     */
    String commitId
    String commitDate
    String commitUrl
    String commitAuthorName
    String commitAuthorEmail
    String commitMsg;
}
