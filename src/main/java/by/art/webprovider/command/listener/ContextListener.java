package by.art.webprovider.command.listener;

import by.art.webprovider.exception.ConnectionPoolException;
import by.art.webprovider.model.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Application listener
 *
 * @author Aliaksandr Artsikhovich
 * @see ServletContextListener
 */
@WebListener
public class ContextListener implements ServletContextListener {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    try {
      ConnectionPool.INSTANCE.initPool();
    } catch (ConnectionPoolException e) {
      logger.error("Connection pool initialization error", e);
      throw new RuntimeException("Connection pool initialization error", e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    try {
      ConnectionPool.INSTANCE.destroyPool();
    } catch (ConnectionPoolException e) {
      logger.error("Connection pool destruction error", e);
      throw new RuntimeException("Connection pool destruction error", e);
    }
  }
}
