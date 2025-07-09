package com.ray;
import java.util.function.Consumer;

/**
 * @author Ray
 * @ClassName TransactionFramework
 * @create 2025/7/8 17:56
 * @Description TODO
 * @Version V1.0
 */

/**
 * 简易事务模拟框架
 */
public class TransactionFramework {

    /**
     * 事务接口
     */
    public interface Transaction {
        void commit() throws Exception;
        void rollback() throws Exception;
    }

    /**
     * 事务管理器
     */
    public static class TransactionManager {
        /**
         * 在事务中执行操作
         * @param transaction 事务对象
         * @param operation 要执行的操作
         * @throws Exception 执行过程中可能抛出的异常
         */
        public static void executeInTransaction(Transaction transaction, Consumer<Transaction> operation) throws Exception {
            try {
                // 执行操作
                operation.accept(transaction);
                // 提交事务
                transaction.commit();
                System.out.println("事务提交成功");
            } catch (Exception e) {
                // 回滚事务
                System.err.println("执行出错，进行事务回滚: " + e.getMessage());
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * 模拟数据库事务实现
     */
    public static class DatabaseTransaction implements Transaction {
        private boolean committed = false;

        @Override
        public void commit() throws Exception {
            // 模拟数据库提交操作
            System.out.println("执行数据库提交...");
            // 这里可以添加实际的数据库提交逻辑
            committed = true;
        }

        @Override
        public void rollback() throws Exception {
            // 模拟数据库回滚操作
            System.out.println("执行数据库回滚...");
            // 这里可以添加实际的数据库回滚逻辑
            committed = false;
        }

        public boolean isCommitted() {
            return committed;
        }
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        // 创建一个数据库事务
        DatabaseTransaction dbTransaction = new DatabaseTransaction();

        try {
            // 在事务中执行操作
            TransactionManager.executeInTransaction(dbTransaction, new Consumer<Transaction>() {
                @Override
                public void accept(Transaction transaction) {
                    // 这里执行业务逻辑
                    System.out.println("执行业务操作...");

                    // 模拟业务逻辑中的错误
//                    if (true) {
//                        throw new RuntimeException("业务操作出错");
//                    }
                }
            });

            System.out.println("事务是否提交成功: " + dbTransaction.isCommitted());
        } catch (Exception e) {
            System.err.println("事务执行失败: " + e.getMessage());
            System.out.println("事务是否提交成功: " + dbTransaction.isCommitted());
        }

    }
}
